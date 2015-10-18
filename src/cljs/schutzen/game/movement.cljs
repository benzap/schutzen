(ns schutzen.game.movement
  (:require [schutzen.utils :refer [log]]
            [schutzen.random :as random]
            [schutzen.array2 :refer [a== aa]]
            [schutzen.game.sensing :as sensing]
            [schutzen.vector :as v]))

(defn generate-random-unit-vector [xrange yrange]
  (let [x (random/pick-value-in-range (- xrange) xrange)
        y (random/pick-value-in-range (- yrange) yrange)
        ]
    (v/unit [x y])))

(defn generate-direction 
  [xrange yrange & 
   {:keys [velocity] 
    :or {velocity 1000}}]
  (let [direction (generate-random-unit-vector xrange yrange)]
    (v/scalar direction velocity)
    ))

(defn change-actor-direction 
  [actor xrange yrange & 
   {:keys [velocity]
    :or {velocity 1000}}]
  (let [[vx vy] (generate-direction xrange yrange :velocity velocity)]
    (a== (-> actor :physics :velocity) (aa vx vy))))

(defn move-to
  [from-actor to-actor
   & {:keys [speed precision]
      :or {speed 200 precision 0.7}}]
  (let [unit-vector (sensing/unit-vector-to-actor from-actor to-actor)
        degree-precision (* (- 1.0 precision) 180.0)
        direction (random/skew-vector-direction unit-vector :degrees degree-precision)
        [vx vy] (v/scalar direction speed)
        ]
    (a== (-> from-actor :physics :velocity) (aa vx vy))
  ))
