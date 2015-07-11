(ns schutzen.utils)

(defn log [& msgs]
  (.apply (.-log js/console) js/console (clj->js (map clj->js msgs)))
  (last msgs))
