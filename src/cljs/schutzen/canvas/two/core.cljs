(ns schutzen.canvas.two.core
  "Contains canvas2d instantiation, and drawing functions"
  (:require [schutzen.utils :refer [log rel-scale]]))

(defn init
  "Creates a <canvas> that fills the current dom element, cwidth and
  cheight represent the relative dimensions of the canvas, and all 2d
  canvas commands will abide to these dimensions"
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

(defn clear [context]
  (.clearRect context 0 0
              (-> context .-canvas .-width)
              (-> context .-canvas .-height)))

(defn draw-image
  "Draw the given image to screen's relative scale"
  [context img x y width height]
  (.drawImage context img x y
              width height))

(defn draw-text
  "Draw the provided text at the given x,y coordinates and with the
  given font family and size"
  [context text x y &
   {:keys [size family color]
    :or {size 14
         family "Monospace"
         color "#FFFFFF"}}]
  (doto context
    (aset "fillStyle" color)
    (aset "font" (str size "px " family)))

  (.fillText context text x y))
