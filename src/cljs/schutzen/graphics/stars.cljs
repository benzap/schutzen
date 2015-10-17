(ns schutzen.graphics.stars
  "Generates stars in the background with parallax side scrolling"
  (:require [schutzen.utils :refer [log]]
            [schutzen.graphics.landscape]
            [schutzen.state :as state]
            [schutzen.canvas.two.core :as c2d]
            [schutzen.random :as random]
            [schutzen.graphics.utils]))

(def space-distribution
  [[:star 1.0]])

(def star-color-distribution 
  [
   ;; Reds
   [0.3 "#ff0000"]
   [0.3 "#F03C02"]
   [0.3 "#C21A01"]

   ;; Whites
   [0.8 "#888888"]
   [0.8 "#ffffff"]

   ;; Greens
   [0.2 "#00ff00"]

   ;; Blues
   [0.2 "#0000ff"]

   ;; Other
   [0.1 "#ffff00"]
   [0.1 "#00ffff"]
   ])

(defrecord Star [x-pos y-pos color width])

(defn create-star
  "Create a Star"
  [x-pos y-pos & 
   {:keys [color width]
    :or {color "#ffffff"
         width 3}}]
  (map->Star 
   {:x-pos x-pos
    :y-pos y-pos
    :color color
    :width width}
   ))

(defn draw-star 
  "Draw a Star onto the screen"
  [canvas star layer-division]
  (let [{x-pos :x-pos y-pos :y-pos color :color width :width} star
        parallax-offset (/ (-> @state/game :viewport :left) layer-division)
        x-pos (schutzen.graphics.utils/correct-viewport-position 
               x-pos
               :layer-division layer-division)
        ]
  (c2d/draw-point canvas 
                  (schutzen.graphics.utils/correct-viewport-offset 
                   x-pos
                   :layer-division layer-division)
                  y-pos
                  :color color 
                  :width width)))

(def upper-bound 10)

;; Stars shouldn't appear within the landscape, so clamp it
(def lower-bound (- state/screen-height 
                    schutzen.graphics.landscape/max-landscape-height))

(def left-bound 0)
(def right-bound state/viewport-width)

(defn generate-random-star 
  [& {:keys [color width]
      :or {color (random/pick-rand-by-dist star-color-distribution)
           width 3}}]
  (let [x-pos (random/pick-value-in-range left-bound right-bound)
        y-pos (random/pick-value-in-range 10 700)
        ;;(random/pick-value-in-range upper-bound lower-bound)
        ]
    (create-star x-pos y-pos :width width :color color)
    ))


(defrecord SpaceLayer [space-list layer-division])

(defn create-spacelayer 
  "A SpaceLayer represents a layer, with a divisor value provided to
  the 'layer-division' to represent how far away the space layer is
  from the main actor layer. For example, providing a value of 2 for
  the layer-division would make the set of space objects appear twice
  as far away as the main actor layer. This means the scrolling back
  and forth in the viewport makes it appear to be farther away."
  [space-list layer-division]
  (map->SpaceLayer 
   {:space-list space-list
    :layer-division layer-division
    }))

(defn generate-spacelayer
  "Generates and draws several stars onto the background"
  [layer-division num-elements]
  (let [space-list
        (for [i (range num-elements)]
          (generate-random-star :width 1))]
    (create-spacelayer space-list layer-division)
    ))

(defn draw-spacelayer [canvas spacelayer]
  (let [{layer-division :layer-division
         space-list :space-list}
        spacelayer]
    (doseq [space-element space-list]
      (draw-star canvas space-element layer-division)
      )))

;; Determines the number of layers of stars to be generated for the
;; scene
(def space-layer-distrib
  [[5 10]
   [10 10]
   [15 10]
   [30 10]])

(defn generate-space []
  (let [spacelayers
        (mapv (fn [[layer num]]
                (generate-spacelayer layer num))
              space-layer-distrib)]
    (swap! state/game assoc :starlayers spacelayers)
    ))

(defn draw-space [context]
  (doseq [spacelayer (-> @state/game :starlayers)]
    (draw-spacelayer context spacelayer)))
