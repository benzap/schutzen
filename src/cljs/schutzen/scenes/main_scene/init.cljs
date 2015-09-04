(ns schutzen.scenes.main-scene.init
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.assets :as assets]
            [schutzen.scenes.scene :refer [SceneRender]]
            [schutzen.canvas.two.core :as c2d]
            [schutzen.actors.ship :as ship]
            [schutzen.graphics.engine]
            [schutzen.array2 :refer [aa a==]]
            ))

(defrecord MainScene [context]
  SceneRender
  (init-scene [_ state]
    (log "Initializing Main Scene")
    (let [ship-actor (ship/create)]
      (a== (-> ship-actor :physics :position) 
           (aa 0 140))
      (a== (-> ship-actor :physics :velocity)
           (aa 10 0))
      (a== (-> ship-actor :physics :acceleration)
           (aa 15 0))
      (log "Ship" ship-actor)
      (log "Graphics" @(-> ship-actor :graphics))

      (state/add-actor! ship-actor)))

  (run-scene [_ state]
    (log "Running Main Scene"))

  (render-scene [_ state delta-ms]
    (c2d/clear context)
    (schutzen.graphics.engine/run-engine context @state/game))

  (pause-scene [_ state]
    (log "Pausing Main Scene")))

(defn create-main-scene [dom]
  (->MainScene (c2d/init dom 640 480)))
 


