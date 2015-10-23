(ns schutzen.random
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.vector :as v]))

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

(defn random-location 
  [& {:keys [min-height max-height]
      :or {min-height 10 
           max-height (- state/screen-height 10)}}]
  (let [x (pick-value-in-range 0 state/viewport-width)
        y (pick-value-in-range min-height max-height)]
    [x y]))

(defn skew-vector-direction
  "randomly changes the direction of the given vector within the range
  of degrees"
  [vec
   & {:keys [degrees]
      :or {degrees 10}}]
  (let [degrees (/ degrees 2)
        mag (v/magnitude vec)
        angle (v/angle vec)
        deg-angle (v/rad->deg angle)
        new-angle (pick-value-in-range (- deg-angle degrees) (+ deg-angle degrees))
        ]
    (-> new-angle v/deg->rad v/rad->unit (v/scalar mag))
    ))

(defn percent-chance
  "based on a provided percent chance between 0.0-100.0, returns true
  if it succeeds"
  [percent]
  (let [distrib 
        [[percent true]
         [(- 100 percent) false]]]
    (pick-rand-by-dist distrib)))

