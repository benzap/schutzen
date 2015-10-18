(ns schutzen.game.logic.actors.lander
  (:require [schutzen.utils :refer [log]]
            [schutzen.game.logic.ai :as ai]
            [schutzen.random :as random]
            [schutzen.game.movement :as movement]
            [schutzen.game.shooting :as shooting]
            [schutzen.array2 :refer [ay]]
            [schutzen.game.player :as player]
            [schutzen.game.sensing :as sensing]))

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

  )
