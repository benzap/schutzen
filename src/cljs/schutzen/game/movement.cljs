(ns schutzen.game.movement
  (:require [schutzen.utils :refer [log]]
            [schutzen.random :as random]))

(defn generate-random-normal-vector [xrange yrange]
  (let [x (random/pick-value-in-range (- xrange) xrange)
        y (random/pick-value-in-range (- yrange) yrange)
        t (+ (Math/abs x) (Math/abs y))]
    [(/ x t) (/ y t)]))

(defn generate-direction 
  [xrange yrange & 
   {:keys [velocity] 
    :or {velocity 1000}}]
  (let [[x y] (generate-random-normal-vector xrange yrange)]
    [(* velocity x) (* velocity y)]
    ))

(log "direction" (generate-direction 10 10 :velocity 1000))

(defn change-actor-direction [actor ])
