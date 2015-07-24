(ns schutzen.utils
  (:require [cljs.core.async :refer [chan close!]])
  (:require-macros [cljs.core.async.macros :as am :refer [go]]))

(defn timeout 
  "Perform a sleep function while in a (go ...) block
   (<! (timeout 1000)) ; 1 second"
  [ms]
  (let [c (chan)]
    (js/setTimeout (fn [] (close! c)) ms)
    c))

(defn log [& msgs]
  (.apply (.-log js/console) js/console (clj->js (map clj->js msgs)))
  (last msgs))

(defn rel-scale
  "Represents a factor on the scale of the original game, based on
  screen resolution. The returned value is multiplied by the relative
  scale"
  [& [val]]
  (-> js/document
      (.querySelector ".schutzen-main")
      (.-clientHeight)
      (/ 480)
      (* (or val 1))))
