(ns schutzen.graphics.utils
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]))

(defn correct-viewport-position
  "This fixes the position of the graphical element, to fit within the
  given viewport"
  [xp & 
   {:keys [layer-division]
    :or {layer-division 1}}]
  (let [xv (-> @state/game :viewport :left)
        xv (/ xv layer-division)
        w state/screen-width]
    (+ (mod (- xp xv) (* 7 w)) xv)
    ))

(defn correct-viewport-offset
  [xp & 
   {:keys [layer-division]
    :or {layer-division 1}}]
  (let [viewport-offset (-> @state/game :viewport :left)]
    (-> xp
        (- (* 3 state/screen-width))
        (- (/ viewport-offset layer-division)))
  ))
