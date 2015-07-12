(ns schutzen.display 
  "Includes functions for manipulating the display")


(defn fix-aspect-and-center 
  "takes a width, height, aspect ratio, and returns a dictionary
  containing the left, top, width, and height values in order to
  maintain that aspect ratio given the current width and height.
  
  The left and top values allow the new display to centered correctly
  within the given width and height section"
  [width height aspect-ratio]
  (let [current-aspect (/ width height)]
    (if (< current-aspect aspect-ratio)
      )))

