(ns schutzen.actors.bomber
  "One of the enemies you encounter"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.collision.event]))

(def sprite-bomber-right (atom nil))

;; Graphics Initialization
(defn init-sprites []
  (reset! sprite-bomber-right 
          (graphics/create-sprite (assets/get-image :bomber)
                                  8 8
                                  :origin [4 4])))

(defn create
  "Create the bomber actor"
  []
  (let [bomber-actor (actor/create "bomber" :enemy)]
    (reset! (-> bomber-actor :graphics) @sprite-bomber-right)
    (reset! (-> bomber-actor :collision) 
            (collision/create-bounding-box [8 8] :origin [4 4]))
    bomber-actor))

(defmethod schutzen.collision.event/on-collision ["bomber" "ship-projectile"]
  [bomber-actor _]
  (log "Bomber Collided With projectile!")
  )
