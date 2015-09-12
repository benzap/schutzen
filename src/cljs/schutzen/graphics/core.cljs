(ns schutzen.graphics.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.assets :as assets]
            [schutzen.canvas.two.core :as c2d]
            [schutzen.state :as state]))

(defn correct-screen-offset-x
  "The virtual screen that we see is placed at a 3 segment offset. We
  correct certain interactive IDrawable objects for this, and apply
  the viewport offset"
  [drawable-x-position]
  (let [viewport-offset (-> @state/game :viewport :left)]
    (-> drawable-x-position
        (- (* 3 state/screen-width))
        (- viewport-offset))))

(defprotocol IDrawable
  (draw [this canvas x-pos y-pos]))

;; Sprite class represents an graphics object that is drawn with a
;; width and height on the screen. Transforms can also be applied to
;; the sprite to further change aspects of what is being seen.
(defrecord Sprite [img width height origin transforms]
  IDrawable
  (draw [_ canvas x-pos y-pos]
    (let [[x-origin y-origin] origin]
      (c2d/draw-image canvas img 
                      (correct-screen-offset-x (- x-pos x-origin)) 
                      (- y-pos y-origin)
                      width height)
      )))

(defn create-sprite 
  "Create a sprite object
  
  Keyword Arguments:

  img -- an Image object containing the image we would like to serve
  as a sprite

  width -- the width of the sprite based on the provided Image

  height -- the height of the sprite based on the provided Image

  transforms -- a map containing transformations

  "
  [img width height & {:keys [origin] :or {origin [0 0]}}]
  (map->Sprite {:img img
                :width width
                :height height
                :origin origin
                :transforms {}}))

