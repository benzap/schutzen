(ns schutzen.game.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.ship :as ship]
            [schutzen.actors.mutant :as mutant]
            [schutzen.game.player :as player]
            [schutzen.state :as state]
            [schutzen.array2 :refer [aa a==]]
            [schutzen.physics.damping :as damping]))

(defn start-game []
  (let [ship-actor (ship/create)]
    (a== (-> ship-actor :physics :position) 
         (aa 2240 240))
    (a== (-> ship-actor :physics :velocity)
         (aa 0 0))
    (a== (-> ship-actor :physics :acceleration)
         (aa 0 0))
    
    (state/add-actor! ship-actor)
    (player/apply-ship-controls! ship-actor)
    )

  (let [mutant-actor (mutant/create)]
    (a== (-> mutant-actor :physics :position)
         (aa 2240 240))
    (state/add-actor! mutant-actor)
    )
)
