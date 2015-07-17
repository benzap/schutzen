(ns schutzen.canvas.two.core
  "Contains canvas2d instantiation, and drawing functions"
  (:require [schutzen.utils :refer [log rel-scale]]))

(defn init
  "Creates a <canvas> that fills the current dom element, and returns
  a 2d context to it."
  [dom]
  (let [dom-canvas (.createElement js/document "canvas")]
    (.appendChild dom dom-canvas)
    (let [width (.-clientWidth dom)
          height (.-clientHeight dom)]
      (doto dom-canvas
        (aset "style" "width" (str width "px"))
        (aset "style" "height" (str height "px"))))
    (.getContext dom-canvas "2d")))

(defn dims [context]
  [(aget context "canvas" "width")
   (aget context "canvas" "height")])

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
  (let [cwidth (-> context .-canvas .-width)
        cheight (-> context .-canvas .-height)])
  (.drawImage
   context img
   (rel-scale x) (rel-scale y)
   (rel-scale width) (rel-scale height)))
            
