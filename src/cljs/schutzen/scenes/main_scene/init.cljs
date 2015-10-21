(ns schutzen.scenes.main-scene.init
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.assets :as assets]
            [schutzen.scenes.scene :refer [SceneRender]]
            [schutzen.canvas.two.core :as c2d]
            [schutzen.graphics.engine]
            [schutzen.graphics.stars]
            [schutzen.graphics.particle]
            ))

(defrecord MainScene [context]
  SceneRender
  (init-scene [_ state]
    (log "Initializing Main Scene"))

  (run-scene [_ state]
    (log "Running Main Scene"))

  (render-scene [_ state delta-sec]
    (c2d/clear context)
    (schutzen.graphics.stars/draw-space context)
    (schutzen.graphics.engine/run-engine context (-> @state/game :actors))
    (schutzen.graphics.particle/run-particle-engine 
     context (-> @state/game :particles) delta-sec)
    )

  (pause-scene [_ state]
    (log "Pausing Main Scene")))

(defn create-main-scene [dom]
  (->MainScene (c2d/init dom state/screen-width state/screen-height)))
