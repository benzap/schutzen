(ns schutzen.physics.force
 "Includes functionality for creating force generators, which are used
  by the physics engine. These forces are registered onto the actor's
  physics component in the :force-gen list.

  Force generators work by establishing forces within the actor's
  force value, which is added to the velocity of the actor on an
  instantaneous basis. The accumulated force is discarded on every
  cycle.

  Creating a force generator involves returning an array with the
  force to apply to the actor ex. (aa 10 0) applies 10 newtons of force downwards

  Note that some force generators don't return a zero force, and apply
  changes to the actor directly ex. damping.
  "
 (:require [schutzen.utils :refer [log]]
           [schutzen.array2 :refer [aa a**i]]
           [schutzen.physics.damping :as damping]))



(defmulti apply-force-generator
  (fn [actor registered-force] (-> registered-force :force-type)))

(defmethod apply-force-generator :damping
  [actor {:keys [damping x-axis-only y-axis-only]}]
  (damping/apply-damping actor damping x-axis-only y-axis-only)
  (aa 0 0))

