(ns schutzen.actors.ship 
  "The main ship actor. I'm assuming that only one is being created"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.canvas.three.object :as object]
            [schutzen.canvas.three.sprite :as sprite]
            [schutzen.canvas.three.material :as material]))

(defn create 
  "Create the ship actor"
  []
  (let [sprite-object (sprite/create-with-asset :ship)
        ship-actor (actor/create sprite-object :actor-name :ship)]
    (aset ship-actor "scale" (THREE.Vector3. 3 3 3))
    (aset ship-actor "position" (THREE.Vector3. 1 0 0))
    ship-actor))

