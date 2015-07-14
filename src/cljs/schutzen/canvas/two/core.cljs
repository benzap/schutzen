(ns schutzen.canvas.two.core
  "Contains canvas2d instantiation, and drawing functions")

(defn init
  "Creates a <canvas> that fills the current dom element, and returns
  a 2d context to it."
  [dom]
  (let [dom-canvas (.createElement js/document "canvas")]
    (.appendChild dom dom-canvas)
    (.getContext dom-canvas "2d")))

(defn fill-canvas
  [context & [color]]
  (let [color (or color "black")]
    ))
