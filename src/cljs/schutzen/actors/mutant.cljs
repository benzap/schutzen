(ns schutzen.actors.mutant
  "One of the enemies you encounter"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.collision.event]))

(def sprite-mutant-right (atom nil))

;; Graphics Initialization
(defn init-sprites []
  (reset! sprite-mutant-right 
          (graphics/create-sprite (assets/get-image :mutant)
                                  16 16
                                  :origin [8 8])))

(defn create
  "Create the mutant actor"
  []
  (let [mutant-actor (actor/create "mutant" :enemy)]
    (reset! (-> mutant-actor :graphics) @sprite-mutant-right)
    (reset! (-> mutant-actor :collision) 
            (collision/create-bounding-box [16 16] :origin [8 8]))
    mutant-actor))

(defmethod schutzen.collision.event/on-collision ["mutant" "ship-projectile"]
  [mutant-actor _]
  (log "Mutant Collided With projectile!")
  )
