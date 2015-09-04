(ns schutzen.physics.engine
  "The engine that decides on the position of actors in a scene"
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [aa ac a**i a++]]))


(defn process-actor 
  "Look at the current position, velocity, and acceleration, and
  change the location of the actor based on changes in delta"
  [actor delta-sec]
  (let [position (-> actor :physics :position)
        velocity (-> actor :physics :velocity)
        acceleration (-> actor :physics :acceleration)]
    
    ;;acceleration increases velocity by the fraction of time
    (a++ velocity (a**i (ac acceleration) delta-sec))

    ;;velocity changes position by the fraction of time
    (a++ position (a**i (ac velocity) delta-sec))

    ))

(defn run-engine [actors delta-sec]
  (doseq [actor actors]
    (process-actor actor delta-sec)))
