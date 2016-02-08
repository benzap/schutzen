(ns schutzen.core
  (:require-macros [cljs.core.async.macros :as am :refer [go]])
  (:require [cljs.test :refer-macros [run-all-tests]]
            [cljs.core.async :refer [chan close!]]
            [schutzen.utils :refer [log timeout]]
            [schutzen.display :as display]
            [schutzen.assets :as assets]
            [schutzen.state :as state]
            [schutzen.scenes.core
             :refer [init-scene-containers]]
            [schutzen.scenes.scene :as scene]
            [schutzen.scenes.status :refer [create-status-scene]]
            [schutzen.scenes.main-scene.init :refer [create-main-scene]]
            [schutzen.scenes.world-map :refer [create-world-map]]

            [schutzen.event :as event]
            [schutzen.camera]
            [schutzen.physics.engine]
            [schutzen.physics.boundary]
            [schutzen.collision.engine]
            [schutzen.game.logic.engine]
            [schutzen.globals :refer [*schutzen-active*]]
            [schutzen.game.core :as game]
            [schutzen.game.actor-manager]
            ;; Actors
            [schutzen.actors.ship]
            [schutzen.actors.mutant]
            [schutzen.actors.lander]
            [schutzen.actors.pod]
            [schutzen.actors.swarmer]
            [schutzen.actors.baiter]
            [schutzen.actors.bomber]
            [schutzen.actors.human]
            ))

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
    (schutzen.physics.boundary/maintain-actor-bounds (-> @state/game :actors))
    (schutzen.physics.engine/run-engine (-> @state/game :actors) (/ 1 60))
    (schutzen.collision.engine/run-engine (-> @state/game :actors) (/ 1 60))
    (schutzen.camera/run-camera-hook (/ 1 60))
    (schutzen.game.logic.engine/run-engine (-> @state/game :actors) (/ 1 60))
    ))

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
        ;; Scene Containers
        containers (init-scene-containers root-dom)]
    ;;initialize our app-state
    (state/init)
    
    ;;Update our app state with opts passed values
    (swap! state/app merge opts)
    

    ;; Run our unit tests when :run-tests? is true
    (when (:run-tests? @state/app)
      (enable-console-print!)
      (run-all-tests #"schutzen\.tests.*"))

    ;;Load our assets. This will block...
    (log "Loading Assets...")
    (assets/load-images (-> @state/app :assets-path))

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

            ;;
            ;; Actor Sprite Initialization
            ;;
            (log "Initializing Actor Assets...")
            (schutzen.actors.ship/init-sprites)
            (schutzen.actors.mutant/init-sprites)
            (schutzen.actors.lander/init-sprites)
            (schutzen.actors.pod/init-sprites)
            (schutzen.actors.swarmer/init-sprites)
            (schutzen.actors.baiter/init-sprites)
            (schutzen.actors.bomber/init-sprites)
            (schutzen.actors.human/init-sprites)

            ;;
            ;; Actor Manager Reservation
            ;;
            (log "Reserving Actors...")
            (schutzen.game.actor-manager/init-manager)
            
            ;;
            ;; Initializing the scenes
            ;;
            (-init root-dom opts containers)))))))

(defn -init [root-dom opts containers]
    ;; initialize, and place our game scenes
    (reset! scene-list
            [
             ;; top-left status bar scene
             (create-status-scene (:top-left-container containers))

             ;; top-middle map scene
             (create-world-map (:top-middle-container containers))

             ;; bottom game scene
             (create-main-scene (:inner-bottom-container containers))
             ])
    
    ;;Initialize our scenes
    (doseq [scene @scene-list]
      (scene/init-scene scene state/game))
    
    ;;Run the game
    (run))

