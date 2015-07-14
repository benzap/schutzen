(ns schutzen.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.display :as display]
            [schutzen.scenes.core :refer [init-scenes] :as scenes]
            [schutzen.globals :refer[*schutzen-active*]]))

(log "Welcome to Schutzen!")

(defn render []
  (.requestAnimationFrame js/window render)
  (when *schutzen-active*
    ;;(log "Render Frame")

    ))

(defn ^:export run []
  (reset! *schutzen-active* true)
  (render))

(defn ^:export pause []
  (reset! *schutzen-active* false))

(defn ^:export init [dom opts]
  (let [opts (or opts #js {})
        opts (js->clj opts :keywordize-keys true)
        {:keys [prompt-startup?]
         :or {:prompt-startup? false}} opts
        ]
    
    ;;initialize, and place our game scene
    (init-scenes dom)

    (log (scenes/rel-scale 1))
    
    (when-not prompt-startup?
      (run))
    ))

