(ns schutzen.physics.core
  "Includes records for representing physics in an Actor")

(defrecord Physics [position velocity acceleration])

(defn create []
  (map->Physics 
   :position (js/Array. 0 0 0)
   :velocity (js/Array. 0 0 0)
   :acceleration (js/Array. 0 0 0)))
