(ns schutzen.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.display :as display]
            [schutzen.assets :as assets]
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
        {:keys [prompt-startup?
                assets-path]
         :or {:prompt-startup? false}} opts
        ;; Scene Containers
        containers (init-scene-containers root-dom)]

    ;;Load our assets
    (log "Loading Assets...")
    (assets/load-images assets-path)
    (log "Done Loading Assets...")
    
    ;; initialize, and place our game scenes
    (reset! scene-list
            [
             ;; top-left status bar scene
             (create-status-scene (:top-left-container containers))

             ;; top-middle map scene
             ;; ...

             ;; bottom game scene
             ;; ...
             ])
    
    ;;Initialize our scenes
    (doseq [scene @scene-list]
      (scene/init-scene scene game-state))
    
    (when-not prompt-startup?
      (run))))

