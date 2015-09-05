(ns schutzen.core
  (:require-macros [cljs.core.async.macros :as am :refer [go]])
  (:require [cljs.core.async :refer [chan close!]]
            [schutzen.utils :refer [log timeout]]
            [schutzen.display :as display]
            [schutzen.assets :as assets]
            [schutzen.state :as state]
            [schutzen.scenes.core
             :refer [init-scene-containers]]
            [schutzen.scenes.scene :as scene]
            [schutzen.scenes.status :refer [create-status-scene]]
            [schutzen.scenes.main-scene.init :refer [create-main-scene]]
            [schutzen.event :as event]
            [schutzen.physics.engine]
            [schutzen.globals :refer [*schutzen-active*]]
            [schutzen.game.core :as game]))

(declare -init)

(log "Welcome to Schutzen!")

;;List scenes to render
(defonce scene-list
  (atom []))

(defn render []
  (.requestAnimationFrame js/window render)
  (when *schutzen-active*
    (doseq [scene @scene-list]
      (scene/render-scene scene state/game (/ 1 60)))
    (event/run-timer-system (/ 1 60))
    (schutzen.physics.engine/run-engine (-> @state/game :actors) (/ 1 60))))

(defn ^:export run []
  (reset! *schutzen-active* true)
  (doseq [scene @scene-list]
    (scene/run-scene scene state/game))
  (game/start-game)
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
    ;;initialize our app-state
    (state/init)
    
    ;;update our assets-path, if provided
    (when assets-path
      (swap! state/app assoc-in [:assets-path] assets-path))
    
    ;;Load our assets. This will block...
    (log "Loading Assets...")
    (assets/load-images assets-path)

    (go    
      (loop [retries 5]
        (if-not (assets/assets-loaded?)
          (do
            (<! (timeout 1000))
            (if-not (zero? retries)
              (recur (dec retries))
              (log "Failed to load all assets")))
          (do
            (log "Loaded Assets!")
            (-init root-dom opts containers)))))))

(defn -init [root-dom opts containers]
    ;; initialize, and place our game scenes
    (reset! scene-list
            [
             ;; top-left status bar scene
             (create-status-scene (:top-left-container containers))

             ;; top-middle map scene
             ;; ...

             ;; bottom game scene
             (create-main-scene (:inner-bottom-container containers))
             ])
    
    ;;Initialize our scenes
    (doseq [scene @scene-list]
      (scene/init-scene scene state/game))
    
    ;;Run the game
    (run))
