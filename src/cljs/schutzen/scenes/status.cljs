(ns schutzen.scenes.status
  "The top-left status bar, which uses a 2d canvas"
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.assets :as assets]
            [schutzen.scenes.scene :refer [SceneRender]]
            [schutzen.canvas.two.core :as c2d]
            [cljs.core.async :as async :refer [<!]]))


(defn show-life-count [context]
  (let [num (-> @state/game :life)
        life-image (assets/get-image :dot)]
    (go
      (c2d/draw-image
       context
       (<! life-image)
       0 0 100 100))))

(defrecord StatusBar [context]
  SceneRender
  (init-scene [_ state]
    (log "Initializing Status Bar")
    (show-life-count context)
    )
  (run-scene [_ state]
    (log "Running Status Bar"))
  (render-scene [_ state delta-ms]
    (log "Rendering Status Bar" delta-ms))
  (pause-scene [_ state]
    (log "Pausing Status Bar")))

(defn create-status-scene [dom]
  (->StatusBar (c2d/init dom)))
