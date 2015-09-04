(ns schutzen.graphics.engine
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [ax ay]]
            [schutzen.graphics.core :as graphics]
            [schutzen.state :as state]))

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
  [canvas game-state]
  (doseq [actor (-> game-state :actors)]
    (draw-actor canvas actor)))
