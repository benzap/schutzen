(ns schutzen.game.player
  (:require [schutzen.utils :refer [log]]
            [schutzen.event :as event]
            [schutzen.array2 :refer [a++ ax+ ay+]]
            [schutzen.actors.ship :as ship]
            [schutzen.state :as state])
  (:require-macros [schutzen.event
                    :refer [on-keydown on-keyup on-timeout]]))

(def player-controls
  {:move-up :w
   :move-down :s
   :thrust :j
   :fire :k
   :bomb :l
   :switch-directions :space
   :hyperspace :g})

(def player-actor (atom nil))

(defn apply-ship-controls! [actor]
  (reset! player-actor actor))

;;
;;Thrust up and down
;;

;;Thrust Up
(on-keydown 
 (:move-up player-controls)
 (when-let [ship-actor @player-actor]
   (ship/move-up ship-actor)
   ))

(on-keyup
 (:move-up player-controls)
 (when-let [ship-actor @player-actor]
   (ship/move-stop ship-actor)
   ))

;;Thrust Down

(on-keydown 
 (:move-down player-controls)
 (when-let [ship-actor @player-actor]
   (ship/move-down ship-actor)
   ))

(on-keyup
 (:move-down player-controls)
 (when-let [ship-actor @player-actor]
   (ship/move-stop ship-actor)
   ))

;; Thrust Right
(on-keydown
 (:thrust player-controls)
 (when-let [ship-actor @player-actor]
   (ship/thrust-left ship-actor)
   ))

(on-keyup
 (:thrust player-controls)
 (when-let [ship-actor @player-actor]
   (ship/thrust-stop ship-actor)
   ))

;; Viewport Testing

(comment
  (on-keydown 
   :left
   (swap! state/game update-in [:viewport :left] + 320)
   )

  (on-keydown
   :right
   (swap! state/game update-in [:viewport :left] - 320)
   )
)
