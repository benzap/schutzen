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
        (aset "canvas" "height" cheight)
        (aset "mozImageSmoothingEnabled" false)
        (aset "webkitImageSmoothingEnabled" false)
        (aset "msImageSmoothingEnabled" false)
        (aset "imageSmoothingEnabled" false)
        )
      (.moveTo context 150 0)
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

(defn draw-path
  "Draw a set of lines from the given list of [x, y] coordinates onto the screen
  
  Keyword Arguments:
  
  context -- canvas context to draw the path onto

  path-listing -- a vector or list containing x and y coordinates 
                  [[x y], ...]

  Optional Arguments:

  color -- color of the stroke used on the path

  width -- width of the stroke used on the path

  line-cap -- type of line cap to use ['butt' 'round' or 'square']

  line-join -- type of line join to use ['miter' 'round' or 'bevel']

  "
  [context path-listing & 
   {:keys [color width line-cap line-join]
    :or {color "#FFFFFF" 
         width 5 
         line-cap "round"
         line-join "round"}}]

  ;;move to the first position
  (let [[x y] (first path-listing)]
    (.beginPath context)
    (.moveTo context x y))
  
  ;;make a path of lines from the position moved to
  (doseq [[x y] (rest path-listing)]
    (.lineTo context x y)
    )

  (aset context "lineWidth" width)
  (aset context "strokeStyle" color)  
  (aset context "lineCap" line-cap)
  (aset context "lineJoin" line-join)
  
  (.stroke context)
  )

(defn draw-point
  "Draws a point on the screen. Current implementation draws small squares"
  [context x y &
   {:keys [color width]
    :or {color "#FFFFFF"
         width 1}}]
  (let [offset (/ width 2)]
    (aset context "fillStyle" color)
    (.fillRect context (- x offset) (- y offset) width width)))

(defn draw-rect
  "Draws a rectangle at the given location"
  [context x y width height & 
   {:keys [color]
    :or {color "#ffffff"}}]
  (aset context "fillStyle" color)
  (.fillRect context x y width height)
  )
