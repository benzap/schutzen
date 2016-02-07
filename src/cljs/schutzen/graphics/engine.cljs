(ns schutzen.graphics.engine
  "This grabs all of the actors within the game state, and draws them
  onto the screen."
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [ax ay ax=]]
            [schutzen.graphics.core :as graphics]
            [schutzen.state :as state]
            [schutzen.canvas.two.core :as c2d]
            [schutzen.collision.core :as collision]
            [schutzen.graphics.utils]))

(defn correct-actor-position
  "this fixes the position of the actor to be placed correctly within
  the viewport. This will only affect the x-position. This makes it so
  that actors that fall off the viewport will wrap around, and
  re-display on the other side of the viewport.
  
  This is based on the equation:
  xp' = [(xp-xv) mod 7w] + xv
  where:
    xp' - the new actor position
    xp  - previous actor position
    xv  - the current viewport offset
    w   - the screen width

  Notes:

  - this works by translating the position of the actor to a zero'd
  position, so that the modulo can correctly offset the position. The
  position is then translated back into the previous position that is
  a representation with respect to the current viewport offset.

  "
  [actor]
  (let [position (-> actor :physics :position)
        xp (ax position)
        ]
    (ax= position (schutzen.graphics.utils/correct-viewport-position xp))
    ))

(defn draw-actor
  "Takes an Actor, and draws it onto the given canvas"
  [canvas actor]
  (let [position (-> actor :physics :position)
        pos-x (ax position)
        pos-y (ay position)
        graphic @(-> actor :graphics)]
    (graphics/draw graphic canvas pos-x pos-y)
    ;; if in dev-mode, draw the collision bounds
    (when-let [collision (-> actor :collision deref)]
      (when (-> @state/app :show-collision-bounds?)
        (collision/draw-collision-outline collision canvas pos-x pos-y)))
    ))

(defn run-engine
  "Grabs all of the actors currently contained in the game state, and
  draws them onto the screen."
  [canvas actors]
  (doseq [actor actors]
    (correct-actor-position actor)
    (draw-actor canvas actor)
    ))
