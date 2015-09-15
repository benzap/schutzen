(ns schutzen.game.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.ship :as ship]
            [schutzen.actors.mutant :as mutant]
            [schutzen.actors.landscape :as landscape]
            [schutzen.game.player :as player]
            [schutzen.state :as state]
            [schutzen.array2 :refer [aa a==]]
            [schutzen.physics.damping :as damping]
            [schutzen.game.landscape]))

(defn start-game []
  ;;Generated landscape
  (let [generated-landscape-actors
        (schutzen.game.landscape/generate-landscape-actors "#B67721" 2)]
    (doseq [landscape-actor generated-landscape-actors]
      (state/add-actor! landscape-actor)
      ))

  (let [ship-actor (ship/create)]
    (a== (-> ship-actor :physics :position) 
         (aa 2240 120))
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
