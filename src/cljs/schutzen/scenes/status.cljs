(ns schutzen.scenes.status
  "The top-left status bar, which uses a 2d canvas"
  (:require [schutzen.utils :refer [log]]
            [schutzen.scenes.scene :refer [SceneRender]]
            [schutzen.canvas.two.core :as c2d]))



(defrecord StatusBar [context]
  SceneRender
  (init-scene [_ state]
    (log "Initializing Status Bar")
    (log (c2d/fill-canvas context "green"))
    )
  (run-scene [_ state]
    (log "Running Status Bar"))
  (render-scene [_ state delta-ms]
    (log "Rendering Status Bar" delta-ms))
  (pause-scene [_ state]
    (log "Pausing Status Bar")))

(defn create-status-scene [dom]
  (->StatusBar (c2d/init dom)))
