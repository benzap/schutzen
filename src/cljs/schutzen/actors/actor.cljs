(ns schutzen.actors.actor
  "Is the simplest representation of something you would draw on the
  main scene canvas. Certain aspects of the actor uses mutable values
  in order to be more performant."
  (:require [schutzen.utils :refer [log]]
            [schutzen.physics.core :as physics]
            [schutzen.graphics.core :as graphics]))

(defonce id-count (atom 0))

(defrecord Actor 
    [name
     id
     type
     physics
     sprite
     collision])

(defn create [name type]
  (let [id (swap! id-count inc)
        physics (physics/create)]
    (map->Actor
     {:name name
      :id id
      :type type
      :physics physics
      :sprite nil
      :collision nil})))

