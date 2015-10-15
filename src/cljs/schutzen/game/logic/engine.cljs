(ns schutzen.game.logic.engine
  (:require [schutzen.utils :refer [log]]
            [schutzen.game.logic.ai :as ai]))

(defn process-actor [actor delta-sec]
  (ai/decrement-state-timer! actor delta-sec)
  (ai/trigger-actor-state actor))

(defn run-engine [actors delta-sec]
  (doseq [actor actors]
    (process-actor actor delta-sec)))
