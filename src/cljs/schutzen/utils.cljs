(ns schutzen.utils)

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
