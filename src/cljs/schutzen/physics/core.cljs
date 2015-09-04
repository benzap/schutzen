(ns schutzen.physics.core
  "Includes records for representing physics in an Actor"
  (:require [schutzen.array2 :refer [aa ax+ ay+ a++]]
            [schutzen.utils :refer [log]]))

(defrecord Physics
    [position velocity acceleration])

(defn create 
  "Actor physics component"
  []
  (map->Physics 
   {:position (aa 0 0)
    :velocity (aa 0 0)
    :acceleration (aa 0 0)}))
