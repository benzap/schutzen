(ns schutzen.collision.core
  "Includes collision components for actors, along with functions for
  resolving whether a collision was made between two actors"
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [ax ay]]
            [schutzen.collision.landscape]
            [schutzen.graphics.utils]
            [schutzen.canvas.two.core :as c2d]
            ))

(defprotocol ICollidable
  (draw-collision-outline [this canvas x y]))

(extend-protocol ICollidable
  PersistentArrayMap
  (draw-collision-outline [this canvas x y]))

(defrecord CollisionBoundingBox [collision-type dimensions origin]
  ICollidable
  (draw-collision-outline [this canvas x y]
    (let [[ox oy] origin
          px (schutzen.graphics.utils/correct-viewport-offset x)
          px (- px ox)
          py (- y ox)
          [width height] dimensions
          ]
      (c2d/draw-rect canvas px py width height :color "#ff0000")
      )
    ))

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
  [dimensions 
   & {:keys [origin] 
      :or {origin [(-> dimensions first (/ 2))
                   (-> dimensions second (/ 2))]}}]
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
        x1 (-> first-position ax schutzen.graphics.utils/correct-viewport-offset)
        x1 (- x1 (-> first-collision :origin first))
        y1 (- (ay first-position) (-> first-collision :origin second))
        ;; first dimensions
        w1 (-> first-collision :dimensions first)
        h1 (-> first-collision :dimensions second)

        second-collision (-> second-actor :collision deref)
        second-position (-> second-actor :physics :position)
        ;; second position with the collision offset
        x2 (-> second-position ax schutzen.graphics.utils/correct-viewport-offset)
        x2 (- x2 (-> second-collision :origin first))
        y2 (- (ay second-position) (-> second-collision :origin second))
        ;; second dimensions
        w2 (-> second-collision :dimensions first)
        h2 (-> second-collision :dimensions second)

        ;;calc temps
        xt (* (Math.abs (- x1 x2)) 2)
        yt (* (Math.abs (- y1 y2)) 2)
        wt (+ w1 w2)
        ht (+ h1 h2)
        ]
    (and (<= xt wt)
         (<= yt ht))))

(defmethod is-collision?
  [:bounding-box :landscape-bound]
  [first-actor second-actor]

  ;; Only check it's a human actor
  (when (= (-> first-actor :name) "human")
    (schutzen.collision.landscape/check-landscape-collision first-actor second-actor))
  )
