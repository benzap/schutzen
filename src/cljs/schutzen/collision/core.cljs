(ns schutzen.collision.core
  "Includes collision components for actors, along with functions for
  resolving whether a collision was made between two actors"
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [ax ay]]))

(defrecord CollisionBoundingBox [collision-type dimensions origin])

(defn create-bounding-box 
  "Create a collision bounding box

  Keyword Arguments:

  dimensions -- vector containing [width height] values of the
  bounding box

  Optional Arguments:  

  origin -- x and y origin of the collision bounding box with respect
  to the position. [default: [0 0]]
  
  Notes:

  - This should be supplied to an Actor object in the :collision atom

  "
  [dimensions & {:keys[origin] :or {origin [0 0]}}]
  (map->CollisionBoundingBox 
   {:collision-type :bounding-box
    :dimensions dimensions
    :origin origin})
  )

(defmulti is-collision? 
  (fn [first-actor second-actor]
    [(-> first-actor :collision deref :collision-type)
     (-> second-actor :collision deref :collision-type)]))

(defmethod is-collision?
  :default
  [first-actor second-actor]
  false)

(defmethod is-collision? 
  [:bounding-box :bounding-box]
  [first-actor second-actor]
  (let [first-collision (-> first-actor :collision deref)
        first-position (-> first-actor :physics :position)
        ;; first position with the collision offset
        x1 (- (ax first-position) (-> first-collision :origin first))
        y1 (- (ay first-position) (-> first-collision :origin second))
        ;; first dimensions
        w1 (-> first-collision :dimensions first)
        h1 (-> first-collision :dimensions second)

        second-collision (-> second-actor :collision deref)
        second-position (-> second-actor :physics :position)
        ;; second position with the collision offset
        x2 (- (ax second-position) (-> second-collision :origin first (or 0)))
        y2 (- (ay second-position) (-> second-collision :origin second (or 0)))
        ;; second dimensions
        w2 (-> second-collision :dimensions first)
        h2 (-> second-collision :dimensions second)

        ;;calc temps
        xt (- x1 x2)
        yt (- y1 y2)
        ]
    (cond
      ;; x not bounded?
      (or (> xt w1) (> (- xt) w2)) false
      ;; y not bounded?
      (or (> yt h1) (> (- yt) h2)) false
      ;; both are bounded
      :else true
      )))
