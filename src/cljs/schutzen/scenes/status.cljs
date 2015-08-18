(ns schutzen.scenes.status
  "The top-left status bar, which uses a 2d canvas"
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.assets :as assets]
            [schutzen.scenes.scene :refer [SceneRender]]
            [schutzen.canvas.two.core :as c2d]
            [cljs.core.async :as async :refer [<!]]))

(defn show-life-count [context life]
  (when-let [img (assets/get-image :ship)]
    (when (>= life 1)
      (c2d/draw-image context img 20 35 48 48))

    (when (>= life 2)
      (c2d/draw-image context img 70 35 48 48))

    (when (>= life 3)
      (c2d/draw-image context img 120 35 48 48))

    (when (>= life 4)
      (c2d/draw-image context img 170 35 48 48))

    (when (>= life 5)
      (c2d/draw-image context img 220 35 48 48))
    
    ))

(defn show-bomb-count [context bombs]
  (when-let [img (assets/get-image :bomb)]
    (when (>= bombs 1)
      (c2d/draw-image context img
                      260 65 24 16))
    (when (>= bombs 2)
      (c2d/draw-image context img
                      260 85 24 16))
    (when (>= bombs 3)
      (c2d/draw-image context img
                      260 105 24 16))
      ))

(defn show-score [context score]
  (let [score (str score)
        cnt (count score)
        nzeroes (apply str (repeat (- 7 cnt) "0"))
        score (str nzeroes score)]
    (c2d/draw-text context score 15 110
                   :color "#00FFFF"
                   :size 60)))

(defrecord StatusBar [context]
  SceneRender
  (init-scene [_ state]
    (log "Initializing Status Bar"))
  (run-scene [_ state]
    (log "Running Status Bar"))
  (render-scene [_ state delta-ms]
    (c2d/clear context)
    (show-life-count context (:life @state))
    (show-bomb-count context (:bombs @state))
    (show-score context (:score @state)))
  (pause-scene [_ state]
    (log "Pausing Status Bar")))

(defn create-status-scene [dom]
  (->StatusBar (c2d/init dom 300 150)))
