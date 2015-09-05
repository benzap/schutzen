(ns schutzen.actors.ship 
  "The main ship actor. I'm assuming that only one is being created"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.array2 :refer [ay= ax=]]
            [schutzen.physics.damping :as damping]))

(defn create 
  "Create the ship actor"
  []
  (let [ship-actor (actor/create "ship" :player)
        ship-sprite-right (graphics/create-sprite (assets/get-image :ship) 48 16)]
    (reset! (-> ship-actor :graphics) ship-sprite-right)
    (damping/add-damping! ship-actor 0.5 :x-axis-only true)
    (log "Ship" ship-actor)
    ship-actor))

;; How fast the ship ascends and descends in elevation
(def ship-elevation-thrust-speed 500.0)

(defn move-up [actor]
  (let [velocity (-> actor :physics :velocity)]
    (ay= velocity (- ship-elevation-thrust-speed))))

(defn move-down [actor]
  (let [velocity (-> actor :physics :velocity)]
    (ay= velocity (+ ship-elevation-thrust-speed))
    (log "velocity" velocity)))

(defn move-stop [actor]
  (let [velocity (-> actor :physics :velocity)]
    (ay= velocity 0.0)
    (log "velocity stop" velocity)))
