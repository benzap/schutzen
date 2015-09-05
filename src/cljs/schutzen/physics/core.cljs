(ns schutzen.physics.core
  "Includes records for representing physics in an Actor
  
  Physics component contains the position of the actor, along with the
  velocity, and acceleration.
  
  "
  (:require [schutzen.array2 :refer [aa ax+ ay+ a++]]
            [schutzen.utils :refer [log]]))

(defrecord Physics
    [position velocity acceleration mass force-gens])

(defn create 
  "Actor physics component"
  [& {:keys [mass] :or {mass 1.0}}]
  (map->Physics 
   {:position (aa 0 0)
    :velocity (aa 0 0)
    :acceleration (aa 0 0)

    ;; mass is used by most force generator operators.
    :mass mass

    ;; the force that will be applied the next time the physics engine
    ;; processes this physics component. As a rule, this is only
    ;; manipulated by the list
    ;; of force generators contained in force-gens. Force Generators
    ;; can be attached to the actor after it's been instantiated.
    :force-gens (atom [])}))
