(ns schutzen.game.sensing
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [ax ay a->v]]
            [schutzen.vector :as v]
            ))

(defn in-proximity? 
  [first-actor second-actor proximity-range]
  (let [[x1 y1] (a->v (-> first-actor :physics :position))
        [x2 y2] (a->v (-> second-actor :physics :position))
        xt (- x2 x1)
        yt (- y2 y1)]
    (< (+ (* xt xt) (* yt yt)) 
       (* proximity-range proximity-range))
    ))

(defn unit-vector-to-actor
  "Get the direction vector from one actor, to another"
  [from-actor to-actor]
  (let [[x1 y1] (a->v (-> from-actor :physics :position))
        [x2 y2] (a->v (-> to-actor :physics :position))
        vx (- x2 x1)
        vy (- y2 y1)
        ]
    (v/unit [vx vy])
    ))
