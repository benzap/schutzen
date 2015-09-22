(ns schutzen.graphics.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.assets :as assets]
            [schutzen.canvas.two.core :as c2d]
            [schutzen.state :as state]
            [schutzen.graphics.landscape]))

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

;; Find the bounds of a graphic with respect to a (0, 0) world
;; position.
(defprotocol IGraphicBounded
  (get-top-bound [this])
  (get-right-bound [this])
  (get-bottom-bound [this])
  (get-left-bound [this]))

;; Sprite class represents an graphics object that is drawn with a
;; width and height on the screen.
(defrecord Sprite [img width height origin transforms]
  IDrawable
  (draw [_ canvas x-pos y-pos]
    (let [[x-origin y-origin] origin]
      (c2d/draw-image canvas img 
                      (- (correct-screen-offset-x x-pos) x-origin)
                      (- y-pos y-origin)
                      width height)
      ))

  IGraphicBounded
  (get-top-bound [this]
    (-> this :origin 
        (get 1) 
        -
        ))
  (get-right-bound [this]
    (-> this :origin 
        (get 0)
        - 
        (+ (:width this))
        ))
  (get-left-bound [this]
    (-> this :origin 
        (get 0) 
        -
        ))
  (get-bottom-bound [this]
    (-> this :origin 
        (get 1) 
        -
        (+ (:height this))
        ))
  )

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

;;
;; Landscape
;;

;; A segment of space consisting of a path, which
;; represents landscape on the surface of the planet.
(defrecord LandscapeSegment [path-listing color width]
  IDrawable
  (draw [_ canvas x-pos y-pos]
    (let [x-pos (correct-screen-offset-x x-pos)]

    
      ;; correct the positioning on the path listing by applying the
      ;; x-position to the x-coordinate of each point on the path
      (let [path-listing (map (fn [[x y] p] [(+ x x-pos) y]) path-listing)]
        (c2d/draw-path canvas path-listing :color color :width width)
        )))
  IGraphicBounded
  (get-top-bound [this] 0)
  (get-right-bound [this]
    schutzen.graphics.landscape/landscape-segment-width)
  (get-left-bound [this] 0)
  (get-bottom-bound [this] 0)
)

(defn create-landscape-segment
  "Create a landscape segment

  Keyword Arguments:

  path-listing -- list or vector representing the segment

  "
  [path-listing & 
   {:keys [color width]
    :or {color "#FFFFFF"
         width 5}}]
  (map->LandscapeSegment
   {:path-listing path-listing
    :color color
    :width width}
   ))
