(ns schutzen.game.player
  (:require [schutzen.utils :refer [log]]
            [schutzen.event :as event]
            [schutzen.array2 :refer [a++ ax+ ay+ a->v]]
            [schutzen.actors.ship :as ship]
            [schutzen.state :as state]
            [schutzen.game.logic.ship-actions]
            [schutzen.graphics.particle]
            )
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
   (ship/thrust ship-actor)
   ))

(on-keyup
 (:thrust player-controls)
 (when-let [ship-actor @player-actor]
   (ship/thrust-stop ship-actor)
   ))

(on-keydown
 (:switch-directions player-controls)
 (when-let [ship-actor @player-actor]
   (ship/toggle-ship-direction ship-actor)
   ))

;; Fire
(on-keydown
 (:fire player-controls)
 (when-let [ship-actor @player-actor]
   (schutzen.game.logic.ship-actions/fire-projectile @player-actor)
   (let [[x y] (a->v (-> ship-actor :physics :position))
         velocity (a->v (-> ship-actor :physics :velocity))]
     (schutzen.graphics.particle/generate-explosion 
      x y 
      :velocity-addition velocity
      :max-width 5.0)
     )
   ))
