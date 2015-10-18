(ns schutzen.vector
  "Set of 2d vector helper functions, on a two-element clojure vector"
  (:require [schutzen.utils :refer [log]]))

(defn magnitude [[x y]]
  (Math/sqrt (+ (* x x) (* y y))))

(defn unit [[x y :as v]]
  (let [m (magnitude v)]
    [(/ x m) (/ y m)]))

(defn scalar 
  "Scalar multiplication of value with vector"
  [[x y] value]
  [(* x value) (* y value)])

(defn angle 
  "angle of vector in radians"
  [[x y]]
  (cond
    (and (= x 0) (>= y 0))
    (/ Math/PI 2)
    (and (= x 0) (< y 0))
    (* (/ Math/PI 2) 3)
    :else
    (let [a (Math/atan (/ (Math/abs y) (Math/abs x)))]
      (case [(pos? x) (pos? y)]
        ;; First Quadrant
        [false true]
        a

        ;; Second Quadrant
        [true true]
        (- Math/PI a)
        
        ;; Third Quadrant
        [true false]
        (+ a Math/PI)

        ;; Fourth Quadrant
        [false false]
        (+ (- Math/PI a) Math/PI)
        ))))

(defn rad->deg [rad]
  (-> rad
      (/ Math/PI)
      (* 180)))

(defn deg->rad [deg]
  (-> deg
      (/ 180)
      (* Math/PI)))

(comment
  (log "Angle of [-1 1]" (rad->deg (angle [-1 1])))
  (log "Angle of [1 1]" (rad->deg (angle [1 1])))
  (log "Angle of [1 -1]" (rad->deg (angle [1 -1])))
  (log "Angle of [-1 -1]" (rad->deg (angle [-1 -1])))
  (log "Angle of [0 1]" (rad->deg (angle [0 1])))
  (log "Angle of [0 -1]" (rad->deg (angle [0 -1])))
)

(defn rad->unit 
  "angle in radians to unit vector"
  [rad]
  (let [x (Math/cos rad) y (Math/sin rad)]
    (unit [(- x) y])
    ))

(comment
  (log "Unit Vector PI" (rad->unit Math/PI))
  (log "Unit Vector 2PI" (rad->unit (* Math/PI 2)))
  (log "Unit Vector PI/2" (rad->unit (/ Math/PI 2)))
)
