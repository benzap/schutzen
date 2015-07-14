(ns schutzen.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.display :as display]
            [schutzen.state :refer [game-state]]
            [schutzen.scenes.core
             :refer [init-scene-containers]]
            [schutzen.scenes.scene :as scene]
            [schutzen.scenes.status :refer [create-status-scene]]
            [schutzen.globals :refer[*schutzen-active*]]))

(log "Welcome to Schutzen!")

;;List scenes to render
(defonce scene-list
  (atom []))

(defn render []
  (.requestAnimationFrame js/window render)
  (when *schutzen-active*
    (doseq [scene @scene-list]
      (scene/render-scene scene game-state (/ 1 60)))))

(defn ^:export run []
  (reset! *schutzen-active* true)
  (doseq [scene @scene-list]
    (scene/run-scene scene game-state))
  (render))

(defn ^:export pause []
  (reset! *schutzen-active* false))

(defn ^:export init [root-dom opts]
  (let [opts (or opts #js {})
        opts (js->clj opts :keywordize-keys true)
        {:keys [prompt-startup?]
         :or {:prompt-startup? false}} opts
        ;; Scene Containers
        containers (init-scene-containers root-dom)
        ;; Instantiating Scenes
        status-scene (create-status-scene (:top-left-container containers))]
    
    ;;initialize, and place our game scenes
    (reset! scene-list [status-scene])

    ;;Initialize our scenes
    (doseq [scene @scene-list]
      (scene/init-scene scene game-state))
    
    (when-not prompt-startup?
      (run))))

