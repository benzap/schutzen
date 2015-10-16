(ns schutzen.game.logic.actors.mutant
  (:require [schutzen.utils :refer [log]]
            [schutzen.game.logic.ai :as ai]
            [schutzen.random :as random]
            [schutzen.game.movement :as movement]
            [schutzen.game.shooting :as shooting]))


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
