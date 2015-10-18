(ns schutzen.game.logic.actors.mutant
  (:require [schutzen.utils :refer [log]]
            [schutzen.game.logic.ai :as ai]
            [schutzen.random :as random]
            [schutzen.game.movement :as movement]
            [schutzen.game.shooting :as shooting]
            [schutzen.game.player :as player]
            [schutzen.game.sensing :as sensing]))

(def mutant-sensing-proximity 350.0)

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
    (when (sensing/in-proximity? ship-actor actor mutant-sensing-proximity)
      (ai/transition-state! actor :chasing)))
  )

(defmethod ai/trigger-actor-state ["mutant" :chasing]
  [actor]

  (when (ai/state-timer-finished? actor)
    ;; Occasionally shoot at ship
    (when (random/percent-chance 30)
      (shooting/fire-at actor @player/player-actor :precision 0.7 :duration 2.0)
    )

    ;; Move towards ship
    (movement/move-to actor @player/player-actor :precision 0.7 :speed 150)
    (ai/set-state-timer! actor (random/pick-value-in-range 0.3 0.7))
    )

  ;; Switch back to roaming when we lose human ship
  (when-not (sensing/in-proximity? actor @player/player-actor mutant-sensing-proximity)
    (ai/transition-state! actor :roaming))
  )
