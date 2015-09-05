(ns schutzen.actors.mutant
  "One of the enemies you encounter"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.array2 :refer [ay= ax=]]
            [schutzen.physics.damping :as damping]))

(defn create
  "Create the mutant actor"
  []
  (let [mutant-actor (actor/create "mutant" :enemy)
        mutant-sprite-right (graphics/create-sprite (assets/get-image :placeholder) 32 32)]
    (reset! (-> mutant-actor :graphics) mutant-sprite-right)
    mutant-actor))

