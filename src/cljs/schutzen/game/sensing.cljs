(ns schutzen.game.sensing
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [ax ay a->v]]
            [schutzen.vector :as v]
            [schutzen.actors.actor :as actor]
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

(defn in-horizontal-proximity? 
  [first-actor second-actor 
   & {:keys [proximity-threshold]
      :or {proximity-threshold 5}}]
  (let [[x1 _] (actor/get-world-center first-actor)
        [x2 _] (actor/get-world-center second-actor)
        proximity (Math/abs (- x2 x1))
        ]
    (< proximity proximity-threshold)
    ))
