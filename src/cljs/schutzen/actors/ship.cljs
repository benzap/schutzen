(ns schutzen.actors.ship 
  "The main ship actor. I'm assuming that only one is being created"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]))

(defn create 
  "Create the ship actor"
  []
  (let [ship-actor (actor/create "ship" :player)
        ship-sprite-right (graphics/create-sprite (assets/get-image :ship) 48 16)]
    (reset! (-> ship-actor :graphics) ship-sprite-right)
    ship-actor))
