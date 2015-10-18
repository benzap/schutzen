(ns schutzen.collision.landscape
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :refer [get-world-bottom-bound
                                           get-world-left-bound
                                           get-world-right-bound]]
            [schutzen.array2 :refer [ax ay]]
            [schutzen.state :as state]
            ))

(defn create-landscape-bound []
  {:collision-type :landscape-bound})

(defn check-landscape-collision [actor landscape]
  (let [xa (-> (+ (get-world-left-bound actor)
                  (get-world-right-bound actor))
               (/ 2))
        ya (get-world-bottom-bound actor)
        
        x-offset (ax (-> landscape :physics :position))
        path-listing (-> landscape :graphics deref :path-listing)
        [x1 y1] (first path-listing)
        [x2 y2] (last path-listing)
        xa (- xa x-offset)

        ;; inverse the y component
        ya (- state/screen-height ya)
        y1 (- state/screen-height y1)
        y2 (- state/screen-height y2)

        ;; get the y-offset
        y-offset (cond
                   (>= y1 y2)
                   y2
                   :else
                   y1)

        ;; get the line slope
        m (/ (- y2 y1) (- x2 x1))
        
        ;; The height of the point where the actor sits above the landscape
        ya-prime (+ (* m xa) y-offset)
        ]
    ;; There's a collision when the actor is past the segment line and
    ;; within the landscape segment
    (and (>= xa 0) (<= ya ya-prime))
    ))
