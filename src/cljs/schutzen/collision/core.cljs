(ns schutzen.collision.core
  "Includes collision components for actors, along with functions for
  resolving whether a collision was made between two actors"
  (:require [schutzen.utils :refer [log]]))

(defrecord CollisionBoundingBox [collision-type dimensions offset])

(defn create-bounding-box 
  "Create a collision bounding box

  Keyword Arguments:

  dimensions -- vector containing [width height] values of the
  bounding box

  Optional Arguments:  

  offset -- x and y offset of the collision bounding box with respect
  to the position. [default: [0 0]]
  
  Notes:

  - This should be supplied to an Actor object in the :collision atom

  "
  [dimensions & {:keys[offset] :or {offset [0 0]}}]
  (map->CollisionBoundingBox 
   {:collision-type :bounding-box
    :dimensions dimensions
    :offset offset})
  )

(defmulti is-collision? 
  (fn [first-actor second-actor]
    [(-> first-actor :collision ref :collision-type)
     (-> second-actor :collision ref :collsion-type)]))

(defmethod is-collision? 
  [:bounding-box :bounding-box]
  )
