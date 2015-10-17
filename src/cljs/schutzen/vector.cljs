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
