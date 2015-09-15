(ns schutzen.graphics.landscape
  "the background landscape consists of a randomly generated background"
  (:require [schutzen.utils :refer [log]]
            [schutzen.random :as random]
            [schutzen.state :as state]))

(def landscape-segments 100)

(def landscape-segment-width (/ state/viewport-width landscape-segments))

(def segment-type-distribution
  [[0.6 :plane]
   [0.5 :shallow-hill-up]
   [0.5 :shallow-hill-down]
   [0.2 :steep-hill-up]
   [0.2 :steep-hill-down]
   [0.1 :sharp]
   ])

(def max-landscape-height 120)
(def min-landscape-height 20)

(def shallow-hill-increment 30)
(def steep-hill-increment 60)

(defn clamp-upper 
  "Make sure our landscape value lies below the max landscape height"
  [val]
  (if (> val max-landscape-height)
    max-landscape-height
    val))

(defn clamp-lower 
  "Make sure our landscape value lies above the min landscape height"
  [val]
  (if (< val min-landscape-height)
    min-landscape-height
    val))

(defmulti generate-landscape-segment (fn [start-y type] type))

(defmethod generate-landscape-segment :plane
  [start-y _]
  [[0 start-y] [landscape-segment-width start-y]])


(defmethod generate-landscape-segment :shallow-hill-up
  [start-y _]
  (let [start start-y
        end (random/pick-value-in-range 
             start-y 
             (clamp-upper (+ start-y shallow-hill-increment)))
        ]
    [[0 start] [landscape-segment-width end]]))

(defmethod generate-landscape-segment :shallow-hill-down
  [start-y _]
  (let [start start-y
        end (random/pick-value-in-range 
             (clamp-lower (- start-y shallow-hill-increment))
             start-y)
        ]
    [[0 start] [landscape-segment-width end]]))

(defmethod generate-landscape-segment :steep-hill-up
  [start-y _]
  (let [start start-y
        end (random/pick-value-in-range 
             start-y 
             (clamp-upper (+ start-y steep-hill-increment)))
        ]
    [[0 start] [landscape-segment-width end]]))

(defmethod generate-landscape-segment :steep-hill-down
  [start-y _]
  (let [start start-y
        end (random/pick-value-in-range 
             (clamp-lower (- start-y steep-hill-increment))
             start-y)
        ]
    [[0 start] [landscape-segment-width end]]))

(defmethod generate-landscape-segment :sharp
  [start-y _]
  (let [center (/ (- max-landscape-height min-landscape-height))]
    (if (> start-y center)
      [[0 start-y] [landscape-segment-width min-landscape-height]]
      [[0 start-y] [landscape-segment-width max-landscape-height]])))

(defn -generate-landscape-listing
  "Generates list of {plot-listing x-position} which represents the
  surface of the current planet"
  []
  (loop [current-segment 0
         landscape-listing []]
    (if (< current-segment landscape-segments)
      (let [landscape-type (random/pick-rand-by-dist segment-type-distribution)
            start-y (if-let [prev-seg (last landscape-listing)]
                      (-> prev-seg :path-listing second second)
                      (/ (- max-landscape-height min-landscape-height) 2)
                      )
            landscape-segment (generate-landscape-segment start-y landscape-type)]
        (recur (inc current-segment)
               (conj landscape-listing
                     {:path-listing landscape-segment 
                      :x-position (* current-segment landscape-segment-width)})))
      landscape-listing
      )))

(defn mirror-y-axis [[x y]]
  [x (- state/screen-height y)])

(defn generate-landscape-listing
  "Corrects the listing to show up at the bottom, since everything in
  the generated landscape is relative to the y-coordinate's zero value
  being the top of the screen.
  
  This also fixes the first and last segments to join up correctly.
  "
  []
  (let [gen-landscape (-generate-landscape-listing)
        corrected-landscape
        (mapv (fn [{:keys [path-listing x-position]}]
               {:path-listing (mapv mirror-y-axis path-listing)
                :x-position x-position}
               ) gen-landscape
                 )
        ;; y-value of last entry
        y-last (-> corrected-landscape last :path-listing second second)
        ]
    (conj (rest corrected-landscape) 
          (-> corrected-landscape
              first
              (assoc-in [:path-listing 0 1] y-last)))
))

(log "Landscape" (generate-landscape-listing))
