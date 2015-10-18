(ns schutzen.actors.lander
  "One of the lander enemies you encounter. These need to be killed in
  order to end the level."
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.collision.event]
            [schutzen.game.logic.ai :as ai]))

(def sprite-lander-right (atom nil))

;; Graphics Initialization
(defn init-sprites []
  (reset! sprite-lander-right
          (graphics/create-sprite (assets/get-image :lander)
                                  16 16
                                  :origin [8 8])))

(defn create
  "Create the mutant actor"
  []
  (let [lander-actor (actor/create "lander" :enemy)]
    (reset! (-> lander-actor :graphics) @sprite-lander-right)
    (reset! (-> lander-actor :collision) 
            (collision/create-bounding-box [16 16] :origin [8 8]))
    (ai/init-actor-logic lander-actor :init)
    lander-actor))

(defmethod schutzen.collision.event/on-collision ["lander" "ship-projectile"]
  [lander-actor _]
  (log "Lander Collided With projectile!")
  )
