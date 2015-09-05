(ns schutzen.game.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.ship :as ship]
            [schutzen.game.player :as player]
            [schutzen.state :as state]
            [schutzen.array2 :refer [aa a==]]))

(defn start-game []
  (let [ship-actor (ship/create)]
    (a== (-> ship-actor :physics :position) 
         (aa (- 320 24) 240))
    (a== (-> ship-actor :physics :velocity)
         (aa 0 0))
    (a== (-> ship-actor :physics :acceleration)
         (aa 0 0))
    
    (state/add-actor! ship-actor)
    (player/apply-ship-controls! ship-actor)
))
