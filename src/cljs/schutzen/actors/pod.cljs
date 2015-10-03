(ns schutzen.actors.pod
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.collision.event]))

(def sprite-pod-right (atom nil))

(defn init-sprites []
  (reset! sprite-pod-right
          (graphics/create-sprite (assets/get-image :pod)
                                  8 8
                                  :origin [4 4])))

(defn create
  "Create the mutant actor"
  []
  (let [pod-actor (actor/create "pod" :enemy)]
    (reset! (-> pod-actor :graphics) @sprite-pod-right)
    (reset! (-> pod-actor :collision) 
            (collision/create-bounding-box [16 16] :origin [8 8]))
    pod-actor))

(defmethod schutzen.collision.event/on-collision ["mutant" "ship-projectile"]
  [pod-actor _]
  (log "Pod Collided With projectile!")
  )
