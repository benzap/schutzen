(ns schutzen.scenes.main-scene.init
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.assets :as assets]
            [schutzen.scenes.scene :refer [SceneRender]]
            [schutzen.canvas.two.core :as c2d]
            [schutzen.actors.ship :as ship]
            ))

(defn show-ship [context]
  (when-let [img (assets/get-image :ship)]
    (c2d/draw-image context img
                    320 240 48 16)))

(defrecord MainScene [context]
  SceneRender
  (init-scene [_ state]
    (log "Initializing Main Scene"))

  (run-scene [_ state]
    (log "Running Main Scene"))

  (render-scene [_ state delta-ms]
    (c2d/clear context)
    (show-ship context))

  (pause-scene [_ state]
    (log "Pausing Main Scene")))

(defn create-main-scene [dom]
  (->MainScene (c2d/init dom 640 480)))
 
