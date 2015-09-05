(ns schutzen.actors.mutant
  "One of the enemies you encounter"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.array2 :refer [ay= ax=]]
            [schutzen.physics.damping :as damping]))

(def sprite-mutant-right (atom nil))

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
    mutant-actor))

