(ns schutzen.collision.partitioning
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.array2 :refer [ax]]))

(def partition-segments 16)
(def partition-segment-width (/ state/viewport-width partition-segments))

(defn value-bounded-to?
  "whether val is in bounds of (low-high)
  ex. (bounded-to? 10 15 20)
   --> 10 < 15 < 20"
  [low val high]
  (and
   (< low val) (< val high)))

(defn box-in-bounds-of-segment?
  "Returns true if the given x-position and width fits within the
  given segment index"
  [x-pos width segment-index]
  (let [viewport-offset (-> @state/game :viewport :left)
        x11 x-pos
        x12 (+ x11 width)
        x21 (-> partition-segment-width
                (* segment-index)
                (+ viewport-offset))
        x22 (-> partition-segment-width
               (* segment-index)
               (+ partition-segment-width)
               (+ viewport-offset))]
    (or
     (value-bounded-to? x21 x11 x22)
     (value-bounded-to? x21 x12 x22)
    )))

(defn actor-in-bounds? [actor segment-index]
  (let [
        x-pos (-> actor :physics :position ax)
        width (-> actor :collision :dimensions)
        ]))

(defn spatial-partition-x [actors]
  (for [segment-index 0
        partition-list (transient [])]
    (if (< segment-index partition-segments)
      (recur (inc segment-index)
             (conj! (filter %() partition-list))
             (persistent! partition-list))
      )))
