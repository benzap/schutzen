(ns schutzen.game.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.ship :as ship]
            [schutzen.actors.ship-projectile :as ship-projectile]
            [schutzen.actors.projectile :as projectile]
            [schutzen.actors.mutant :as mutant]
            [schutzen.actors.landscape :as landscape]
            [schutzen.game.player :as player]
            [schutzen.state :as state]
            [schutzen.array2 :refer [aa a==]]
            [schutzen.game.landscape]
            [schutzen.graphics.stars]
            [schutzen.game.actor-manager :as actor-manager]
            [schutzen.random :as random]))

(defn start-game []
  ;; Generated Stars
  (schutzen.graphics.stars/generate-space)

  ;; Generated landscape
  (let [generated-landscape-actors
        (schutzen.game.landscape/generate-landscape-actors "#B67721" 2)]
    (doseq [landscape-actor generated-landscape-actors]
      (state/add-actor! landscape-actor)
      ))

  ;; Test Player Ship
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

  ;; Test Player Projectile
  (let [ship-projectile-actor (ship-projectile/create)]
    (a== (-> ship-projectile-actor :physics :position) 
         (aa 2240 117))
    (a== (-> ship-projectile-actor :physics :velocity)
         (aa 1500 0))    

    (state/add-actor! ship-projectile-actor)
    )

  ;;Test Enemy Projectile
  (let [projectile-actor (projectile/create)]
    (a== (-> projectile-actor :physics :position) 
         (aa 2240 200))
    (a== (-> projectile-actor :physics :velocity)
         (aa 300 0))    

    (state/add-actor! projectile-actor)
    )  


  (let [mutant-actor (mutant/create)]
    (a== (-> mutant-actor :physics :position)
         (aa 2240 240))
    (state/add-actor! mutant-actor)
    )

  (doseq [i (range 5)]
    (let [mutant (actor-manager/allocate-actor! :mutant)
          [x y] (random/random-location)]
      (a== (-> mutant :physics :position)
           (aa x y))
      (state/add-actor! mutant)
      ))

)
