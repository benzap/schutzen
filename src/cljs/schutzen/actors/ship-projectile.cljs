(ns schutzen.actors.ship-projectile
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.array2 :refer [ax= ay=]]))

(def projectile-speed 1500.0)

(defn create
  "Create a ship projectile"
  []
  (let [ship-projectile-actor 
        (actor/create "ship-projectile" :projectile)]
    (reset! (-> ship-projectile-actor :graphics) (graphics/create-rectangle 50 1 :origin [25 0.5]))
    (reset! (-> ship-projectile-actor :collision)
            (collision/create-bounding-box [50 1] :origin [25 0.5]))
    ship-projectile-actor))
