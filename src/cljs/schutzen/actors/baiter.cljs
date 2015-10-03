(ns schutzen.actors.baiter
  "One of the enemies you encounter"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.collision.event]))

(def sprite-baiter-right (atom nil))

;; Graphics Initialization
(defn init-sprites []
  (reset! sprite-baiter-right 
          (graphics/create-sprite (assets/get-image :baiter)
                                  16 8
                                  :origin [8 4])))

(defn create
  "Create the baiter actor"
  []
  (let [baiter-actor (actor/create "baiter" :enemy)]
    (reset! (-> baiter-actor :graphics) @sprite-baiter-right)
    (reset! (-> baiter-actor :collision) 
            (collision/create-bounding-box [16 8] :origin [8 4]))
    baiter-actor))

(defmethod schutzen.collision.event/on-collision ["baiter" "ship-projectile"]
  [baiter-actor _]
  (log "Baiter Collided With projectile!")
  )
