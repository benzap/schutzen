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
            [schutzen.random :as random]
            [schutzen.collision.landscape]
            [schutzen.game.logic.core]
            [schutzen.graphics.particle]
            ))

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

  (let [mutant-actor (mutant/create)]
    (a== (-> mutant-actor :physics :position)
         (aa 2240 240))
    (state/add-actor! mutant-actor)
    )

  ;; Randomly generated tests

  (doseq [i (range 2)]
    (let [mutant (actor-manager/allocate! :mutant)
          [x y] (random/random-location)]
      (a== (-> mutant :physics :position)
           (aa x y))
      ))

  (doseq [i (range 2)]
    (let [lander (actor-manager/allocate! :lander)
          [x y] (random/random-location)]
      (a== (-> lander :physics :position)
           (aa x y))
      ))

  (doseq [i (range 2)]
    (let [pod (actor-manager/allocate! :pod)
          [x y] (random/random-location)]
      (a== (-> pod :physics :position)
           (aa x y))
      ))

  (doseq [i (range 2)]
    (let [swarmer (actor-manager/allocate! :swarmer)
          [x y] (random/random-location)]
      (a== (-> swarmer :physics :position)
           (aa x y))
      ))

  (doseq [i (range 2)]
    (let [baiter (actor-manager/allocate! :baiter)
          [x y] (random/random-location)]
      (a== (-> baiter :physics :position)
           (aa x y))
      ))

  (doseq [i (range 2)]
    (let [bomber (actor-manager/allocate! :bomber)
          [x y] (random/random-location)]
      (a== (-> bomber :physics :position)
           (aa x y))
      ))

  (doseq [i (range 2)]
    (let [human (actor-manager/allocate! :human)
          [x y] (random/random-location)
          y (schutzen.collision.landscape/get-landscape-height-at-position x)
          ]
      (a== (-> human :physics :position)
           (aa x y))
      ))

  (doseq [i (range 10)]
    (let [vx (random/pick-value-in-range -200 200)
          vy (random/pick-value-in-range -200 200)
          particle
          (schutzen.graphics.particle/create-particle [2240 120] [vx vy]
                                                      (random/pick-value-in-range 0.5 1.0))
          ]
      (state/add-particle! particle)
      ))
)
