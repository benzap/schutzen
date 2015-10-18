(ns schutzen.actors.human
  "Human actor, which sits on the surface"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.random :as random]
            [schutzen.collision.event]
            [schutzen.game.logic.ai :as ai]))

(def sprite-human (atom nil))

;; Graphics Initialization
(defn init-sprites []
  (reset! sprite-human 
          (graphics/create-sprite (assets/get-image :human)
                                  8 32)))

(defn create
  "Create the mutant actor"
  []
  (let [human-actor (actor/create "human" :friendly)]
    (reset! (-> human-actor :graphics) @sprite-human)
    (reset! (-> human-actor :collision) 
            (collision/create-bounding-box [8 32]))
    (ai/init-actor-logic human-actor :init)
    human-actor))

(defmethod schutzen.collision.event/on-collision ["human" "ship-projectile"]
  [human-actor _]
  (log "Human Collided With projectile!")
  (log human-actor)
  )

(defmethod schutzen.collision.event/on-collision ["human" "landscape"]
  [human-actor _]
  (log "Human is on landscape"))
