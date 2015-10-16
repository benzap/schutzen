(ns schutzen.game.movement
  (:require [schutzen.utils :refer [log]]
            [schutzen.random :as random]
            [schutzen.array2 :refer [a== aa]]))

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

(defn change-actor-direction 
  [actor xrange yrange & 
   {:keys [velocity]
    :or {velocity 1000}}]
  (let [[vx vy] (generate-direction xrange yrange :velocity velocity)]
    ()
    (a== (-> actor :physics :velocity) (aa vx vy))))
