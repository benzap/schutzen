(ns schutzen.core
  (:require [schutzen.utils :refer [log]]))

(.log js/console "hello schtuzen!")

(defn init [dom opts]
  (let [opts (or opts #js {})
        opts (js->clj opts :keywordize-keys true)
        {:keys [prompt-startup?]
         :or {:prompt-startup? false}} opts]

    

    ))

(defn run [])

(defn pause[])
