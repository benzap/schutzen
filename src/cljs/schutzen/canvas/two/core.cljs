(ns schutzen.canvas.two.core
  "Contains canvas2d instantiation, and drawing functions"
  (:require [schutzen.utils :refer [log rel-scale]]))

(defn init
  "Creates a <canvas> that fills the current dom element, rel-width
  and rel-height represent the relative dimensions of the canvas, and
  all 2d canvas commands will abide to this relative scale"
  [dom cwidth cheight]
  (let [dom-canvas (.createElement js/document "canvas")]
    (.appendChild dom dom-canvas)
    (let [width (.-clientWidth dom)
          height (.-clientHeight dom)
          context (.getContext dom-canvas "2d")]
      (doto dom-canvas
        (aset "style" "width" (str width "px"))
        (aset "style" "height" (str height "px")))
      (doto context
        (aset "canvas" "width" cwidth)
        (aset "canvas" "height" cheight))
    context)))

(defn fill-canvas
  [context & [color]]
  (let [color (or color "black")
        [width height] (dims context)]
    (doto context
      (aset "fillStyle" color)
      (.fillRect 0 0 width height))))

(defn draw-image
  "Draw the given image to screen's relative scale"
  [context img x y width height]
  (.drawImage context img x y
              width height))
            
