(ns schutzen.actors.swarmer
  "One of the enemies you encounter"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.collision.event]))

(def sprite-swarmer-right (atom nil))

;; Graphics Initialization
(defn init-sprites []
  (reset! sprite-swarmer-right 
          (graphics/create-sprite (assets/get-image :swarmer)
                                  8 8
                                  :origin [4 4])))

(defn create
  "Create the swarmer actor"
  []
  (let [swarmer-actor (actor/create "swarmer" :enemy)]
    (reset! (-> swarmer-actor :graphics) @sprite-swarmer-right)
    (reset! (-> swarmer-actor :collision) 
            (collision/create-bounding-box [8 8] :origin [4 4]))
    swarmer-actor))

(defmethod schutzen.collision.event/on-collision ["swarmer" "ship-projectile"]
  [swarmer-actor _]
  (log "Swarmer Collided With projectile!")
  )
