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

(defn get-landscape-height-at-position 
  "Based on the provided x position, provides a height value to place
  an actor on the landscape"
  [x]

  ;; Determine the landscape actor, or return the bottom of the screen
  (if-let [landscape
           (first (filter 
                   (fn [actor]
                     (and
                      (= (-> actor :name) "landscape")
                      (let [x-pos (ax (-> actor :physics :position))
                            path-listing (-> actor :graphics deref :path-listing)
                            [x1 _] (first path-listing)
                            [x2 _] (last path-listing)
                            
                            x1 (+ x1 x-pos)
                            x2 (+ x2 x-pos)
                            ]
                        (and (>= x x1) (< x x2))
                        )))
                   (-> @state/game :actors)))]

    ;; Based the slope of the landscape, get the height
    (let [path-listing (-> landscape :graphics deref :path-listing)
          x-pos (ax (-> landscape :physics :position))
          [x1 y1] (first path-listing)
          [x2 y2] (last path-listing)
          
          ;;set the x origin to the landscape
          x (- x x1 x-pos)

          ;; inverse the y component
          y1 (- state/screen-height y1)
          y2 (- state/screen-height y2)

          ;; get the y-offset
          y-offset (if (>= y1 y2) y2 y1)

          ;; get the line slope
          m (/ (- y2 y1) (- x2 x1))
          ;; The height of the point where the actor sits above the landscape
          ya-prime (+ (* m x) y-offset)
          ]
      (- state/screen-height ya-prime)
      )
    state/screen-height))

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
