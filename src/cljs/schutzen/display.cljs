(ns schutzen.display 
  "Includes functions for manipulating the display"
  (:require [schutzen.utils :refer [log]]))


(defn fix-aspect-and-center 
  "takes a width, height, aspect ratio, and returns a dictionary
  containing the left, top, width, and height values in order to
  maintain that aspect ratio given the current width and height.
  
  The left and top values allow the new display to centered correctly
  within the given width and height section"
  [width height & [aspect-ratio]]
  (let [aspect-ratio (or aspect-ratio (/ 4 3))
        current-aspect (/ width height)]
    (if (< current-aspect aspect-ratio)
      {:width width
       :height (/ width aspect-ratio)
       :left 0
       :top (/ (- height (/ width aspect-ratio)) 2)}
      {:width (* height aspect-ratio)
       :height height
       :left (/ (- width (* height aspect-ratio)) 2)
       :top 0}
      )))

;;(log (fix-aspect-and-center 1000 300))
