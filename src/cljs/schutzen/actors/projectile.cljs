(ns schutzen.actors.projectile
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.array2 :refer [ax= ay=]]))

(defn create
  "Create a common enemy projectile"
  []
  (let [projectile-actor
        (actor/create "enemy-projectile" :projectile)]
    (reset! (-> projectile-actor :graphics) (graphics/create-rectangle 3 3 :origin [1.5 1.5]))
    (reset! (-> projectile-actor :collision)
            (collision/create-bounding-box [3 3] :origin [1.5 1.5]))
    projectile-actor))
