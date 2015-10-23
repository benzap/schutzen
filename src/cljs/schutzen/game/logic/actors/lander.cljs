(ns schutzen.game.logic.actors.lander
  (:require [schutzen.utils :refer [log]]
            [schutzen.game.logic.ai :as ai]
            [schutzen.random :as random]
            [schutzen.game.movement :as movement]
            [schutzen.game.shooting :as shooting]
            [schutzen.collision.event]
            [schutzen.actors.actor :as actor]
            [schutzen.array2 :refer [ay a== aa a->v]]
            [schutzen.game.player :as player]
            [schutzen.game.sensing :as sensing]
            [schutzen.state :as state]))

(def low-roaming-transition 240.0)

(defmethod ai/trigger-actor-state ["lander" :init]
  [actor]
  (ai/transition-state! actor :high-roaming)
  )

(defmethod ai/trigger-actor-state ["lander" :high-roaming]
  [actor]
  
  ;; Roaming Direction Switch
  (when (ai/state-timer-finished? actor)
    (movement/change-actor-direction actor 100 500
                                     :velocity (random/pick-value-in-range 50 100))

    (ai/set-state-timer! actor (random/pick-value-in-range 0.5 1.0))


    ;; am I at an elevation where I should switch to low-roaming?
    (when (> (ay (-> actor :physics :position)) low-roaming-transition)
      (ai/transition-state! actor :low-roaming)
      )
    )
  )

(defmethod ai/trigger-actor-state ["lander" :low-roaming]
  [actor]

  ;; Roaming Direction Switch
  (when (ai/state-timer-finished? actor)
    (movement/change-actor-direction actor 500 0
                                     :velocity (random/pick-value-in-range 50 100))    

    (ai/set-state-timer! actor (random/pick-value-in-range 0.5 1.0))
    )

  (if-let [humans (filter #(= (:name %) "human")
                          (-> @state/game :actors))]
    (doseq [human-actor humans]
      (when (sensing/in-horizontal-proximity? actor human-actor
                                              :proximity-threshold 10)
        (ai/transition-state! actor :beaming-human)
        )
      ))
  )

(defmethod ai/trigger-actor-state ["lander" :beaming-human]
  [actor]
  (a== (-> actor :physics :velocity) (aa 0 10)))

;; logic state set when lander collides with human
(defmethod schutzen.collision.event/on-collision ["lander" "human"]
  [lander-actor human-actor]
  (log "Collision!" human-actor)
  (ai/transition-state! lander-actor :abducting-human human-actor)
  )

(defmethod ai/trigger-actor-state ["lander" :abducting-human]
  [actor]
  ;; Start abducting human
  (a== (-> actor :physics :velocity) (aa 0 -10))
  (let [human-actor (ai/get-state-arguments actor)]
    (if-let [human-actor
             (first (filter #(= % human-actor)
                            (-> @state/game :actors)))]
      (let [[cx cy] (actor/get-world-center actor)]
        (log "moving stuff")
        (a== (-> human-actor :physics :position) (aa (+ cx 5) (+ cy 16)))
        )
      (ai/transition-state! actor :high-roaming)
    )))
