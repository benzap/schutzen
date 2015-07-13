(ns schutzen.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.display :as display]
            [schutzen.globals :refer[*schutzen-active*]]))

(.log js/console "hello schtuzen!")

(defn render []
  (.requestAnimationFrame js/window render)
  (when *schutzen-active*
    (log "Render Frame")

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

        ;;get a threejs renderer
        renderer nil

        ;;
    
        ]
    (when-not prompt-startup?
      (run))
    ))
