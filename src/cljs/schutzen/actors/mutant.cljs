(ns schutzen.actors.mutant
  "One of the enemies you encounter"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.game.movement :as movement]
            ;;[schutzen.game.shooting :as shooting]
            [schutzen.random :as random]
            [schutzen.collision.event]
            [schutzen.game.logic.ai :as ai]))

(def sprite-mutant-right (atom nil))

;; Graphics Initialization
(defn init-sprites []
  (reset! sprite-mutant-right 
          (graphics/create-sprite (assets/get-image :mutant)
                                  16 16
                                  :origin [8 8])))

(defn create
  "Create the mutant actor"
  []
  (let [mutant-actor (actor/create "mutant" :enemy)]
    (reset! (-> mutant-actor :graphics) @sprite-mutant-right)
    (reset! (-> mutant-actor :collision) 
            (collision/create-bounding-box [16 16] :origin [8 8]))
    (ai/init-actor-logic mutant-actor :init)
    mutant-actor))

(defmethod schutzen.collision.event/on-collision ["mutant" "ship-projectile"]
  [mutant-actor _]
  (log "Mutant Collided With projectile!")
  )

(defmethod ai/trigger-actor-state ["mutant" :init]
  [actor]
  (ai/transition-state! actor :roaming)
  )

(defmethod ai/trigger-actor-state ["mutant" :roaming]
  [actor]
  (when (ai/state-timer-finished? actor)
    (movement/change-actor-direction actor 500 300 
                                     :velocity (random/pick-value-in-range 200 300))
    (ai/set-state-timer! actor (random/pick-value-in-range 0.1 0.5))
    ;;(shooting/fire-from-actor actor :velocity [200 0] :duration 1.0)
    ))
