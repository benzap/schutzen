(ns schutzen.random
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]))

(def dist-resolution 10000000)

(defn get-distribution [norm-factor tupl]
  (loop [cstart 0
         tupl tupl
         distrib []]
    (if-let [tup (first tupl)]
      (let [[percent value] tup
            cend (+ cstart (* percent norm-factor))]
        (recur cend
               (rest tupl)
               (conj distrib [[cstart cend] value])))
      distrib)))

(defn pick-rand-by-dist- [ds]
  (let [r (rand dist-resolution)]
    (->> ds
         (filter (fn [[[start end] value]]
                   (and (<= start r)
                        (> end r))))
         first
         second)))

(defn pick-rand-by-dist 
  [tupl]
  (let [total-percent (->> tupl
                           (map first)
                           (reduce +))
        norm-factor (/ dist-resolution total-percent)
        distrib (get-distribution norm-factor tupl)]
    (pick-rand-by-dist- distrib)))

(defn pick-value-in-range [start end]
  (let [t (- end start)]
    (+ (rand t) start)))

(defn random-location []
  (let [x (pick-value-in-range 0 state/viewport-width)
        y (pick-value-in-range 10 (- state/screen-height 10))]
    [x y]))
