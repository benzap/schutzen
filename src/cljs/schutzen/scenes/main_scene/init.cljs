(ns schutzen.scenes.main-scene.init
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.assets :as assets]
            [schutzen.scenes.scene :refer [SceneRender]]
            [schutzen.canvas.three.core :as c3d]
            [schutzen.canvas.three.object :as object]
            [schutzen.actors.ship :as ship]
            ))

(defrecord MainScene [renderer scene camera]
  SceneRender
  (init-scene [_ state]
    (log "Initializing Main Scene"))

  (run-scene [_ state]
    (let [ship-actor (ship/create)]
      (.add scene ship-actor)
      (log "Ship Actor" ship-actor)
      (log "Camera" camera)
      (object/translate! camera 0 0 20)))

  (render-scene [_ state delta-ms]
    (.render renderer scene camera))

  (pause-scene [_ state]
    (log "Pausing Main Scene")))

(defn create-main-scene [dom]
  (map->MainScene (c3d/init dom)))
 
