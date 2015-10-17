(ns schutzen.game.logic.actors.mutant
  (:require [schutzen.utils :refer [log]]
            [schutzen.game.logic.ai :as ai]
            [schutzen.random :as random]
            [schutzen.game.movement :as movement]
            [schutzen.game.shooting :as shooting]
            [schutzen.game.player :as player]
            [schutzen.game.sensing :as sensing]))


(defmethod ai/trigger-actor-state ["mutant" :init]
  [actor]
  (ai/transition-state! actor :roaming)
  )

(defmethod ai/trigger-actor-state ["mutant" :roaming]
  [actor]
  
  ;; Roaming Direction Switch
  (when (ai/state-timer-finished? actor)
    (movement/change-actor-direction actor 500 100 
                                     :velocity (random/pick-value-in-range 100 200))
    (ai/set-state-timer! actor (random/pick-value-in-range 0.3 0.7))
    ;;(shooting/fire-from-actor actor :velocity [200 0] :duration 1.0)
    )

  ;; Proximity Checks
  (when-let [ship-actor @player/player-actor]
    (when (sensing/in-proximity? ship-actor actor 200)
      (ai/transition-state! actor :chasing)))
  )

(defmethod ai/trigger-actor-state ["mutant" :chasing]
  [actor]
  (log "Within proximity, chasing")
  (ai/transition-state! actor :roaming))
