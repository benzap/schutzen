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
      :or {color "#ffffff"
           width 3}}]
  (let [x-pos (random/pick-value-in-range left-bound right-bound)
        y-pos (random/pick-value-in-range upper-bound lower-bound)]
    (log "Generated Star" x-pos y-pos)
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
          (generate-random-star :color "#ffffff" :width 1))]
    (create-spacelayer space-list layer-division)
    ))

(defn draw-spacelayer [canvas spacelayer]
  (let [{layer-division :layer-division
         space-list :space-list}
        spacelayer]
    (doseq [space-element space-list]
      (draw-star canvas space-element layer-division)
      )))

