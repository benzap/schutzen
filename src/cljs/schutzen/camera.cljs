(ns schutzen.camera
  "system within the game loop for moving the viewport to track the player's ship."
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.game.player :as player]
            [schutzen.array2 :refer [ax]]))

(def camera-left-anchor 
  (-> (* state/screen-width 3.5)
      (- (* state/screen-width (/ 1 4)))
      ))

(def camera-right-anchor
  (-> (* state/screen-width 3.5)
      (+ (* state/screen-width (/ 1 4)))
      ))

(def camera-speed 600.0)

(defn run-camera-hook 
  "Follows the player ship, and corrects the camera"
  [delta-sec]
  (when-let [ship-actor @player/player-actor]
    (let [pos-x (-> ship-actor :physics :position ax)
          vel-x (-> ship-actor :physics :velocity ax)
          velocity-delta (* vel-x delta-sec)
          ship-direction (-> ship-actor :state deref :ship-direction)
          viewport-offset (-> @state/game :viewport :left)
          camera-offset 
          (+ viewport-offset (if (= ship-direction :right)
                               camera-left-anchor
                               camera-right-anchor))
          target-delta (- camera-offset pos-x)
          camera-delta (* camera-speed delta-sec)
          camera-delta (if (>= target-delta 0) camera-delta (- camera-delta))]
      (cond
        (or (and (>= camera-delta (/ target-delta 2)) (>= target-delta 0))
            (and (< camera-delta (/ target-delta 2)) (< target-delta 0)))
        (swap! state/game update-in [:viewport :left] - target-delta)

        :else
        (swap! state/game update-in [:viewport :left] - (- camera-delta velocity-delta))
      ))))
