(ns schutzen.graphics.engine
  "This grabs all of the actors within the game state, and draws them
  onto the screen."
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [ax ay]]
            [schutzen.graphics.core :as graphics]))

(defn draw-actor
  "Takes an Actor, and draws it onto the given canvas"
  [canvas actor]
  (let [position (-> actor :physics :position)
        pos-x (ax position)
        pos-y (ay position)
        graphic @(-> actor :graphics)]
    (graphics/draw graphic canvas pos-x pos-y)))

(defn run-engine
  "Grabs all of the actors currently contained in the game state, and
  draws them onto the screen."
  [canvas actors]
  (doseq [actor actors]
    (draw-actor canvas actor)))
