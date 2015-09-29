(ns schutzen.scenes.world-map
  "The top-middle world map, which uses a 2d canvas"
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.scenes.scene :refer [SceneRender]]
            [schutzen.canvas.two.core :as c2d]
            [schutzen.array2 :refer [ax ay]]))

(def map-width 640)
(def map-segment-width (/ map-width 7))
(def map-height 120)

(defn get-corrected-actor-position [actor]
  (let [position (-> actor :physics :position)
        viewport-offset (-> @state/game :viewport :left)
        x (- (ax position) 
             viewport-offset)
        y (ay position)
        x-ratio (/ map-width state/viewport-width)
        y-ratio (/ (- map-height 15) state/screen-height)]
    [(* x x-ratio 2) (* y y-ratio 2)]
    ))

(defn draw-map-point 
  [context actor & 
   {:keys [color width]
    :or {color "#ff0000"
         width 3}}]
  (let [[x y] (get-corrected-actor-position actor)]
    (c2d/draw-point context x y :color color :width width)
    ))

(defmulti draw-actor-on-map 
  (fn [context actor] [(:name actor) (:type actor)]))

(defmethod draw-actor-on-map :default
  [context actor])

(defmethod draw-actor-on-map ["mutant" :enemy]
  [context actor]
  (draw-map-point context actor :color "#00ff00"))

(defmethod draw-actor-on-map ["ship" :player]
  [context actor]
  (draw-map-point context actor :color "#ff0000" :width 5))

(defn draw-map-actors [context]
  (doseq [actor (-> @state/game :actors)]
    (draw-actor-on-map context actor)
    ))

(defn draw-map-outline [context]
  (c2d/draw-path 
   context 
   [[(* 3 map-segment-width) 90]
    [(* 3 map-segment-width) (- map-height 7)]
    ] :width 2 :line-cap "square")
  (c2d/draw-path 
   context 
   [[(* 4 map-segment-width) 90]
    [(* 4 map-segment-width) (- map-height 7)]
    ] :width 2 :line-cap "square")
  (c2d/draw-path
   context
   [[(+ (* 3 map-segment-width) 4.0) (- map-height 7)]
    [(- (* 4 map-segment-width) 4.0) (- map-height 7)]
    ] :width 10 :line-cap "square")
  )

(defrecord WorldMap [context]
  SceneRender
  (init-scene [_ state]
    (log "Initializing World Map"))
  (run-scene [_ state]
    (log "Running World Map"))
  (render-scene [_ state delta-ms]
    (c2d/clear context)
    (draw-map-outline context)
    (draw-map-actors context)
    )
  (pause-scene [_ state]))

(defn create-world-map [dom]
  (->WorldMap (c2d/init dom map-width map-height)))
