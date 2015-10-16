(ns schutzen.actors.ship-projectile
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.array2 :refer [ax= ay= a== aa]]
            ))

(defn create
  "Create a ship projectile"
  []
  (let [ship-projectile-actor 
        (actor/create "ship-projectile" :projectile)]
    (reset! (-> ship-projectile-actor :graphics) (graphics/create-rectangle 60 1 :origin [30 0.5]))
    (reset! (-> ship-projectile-actor :collision)
            (collision/create-bounding-box [60 1] :origin [30 0.5]))
    ship-projectile-actor))
