(ns schutzen.actors.actor
  "Is the simplest representation of something you would draw on the
  main scene canvas. Certain aspects of the actor uses mutable values
  in order to be more performant.
  
  Notes:
  
  /nothing yet/

  "
  (:require [schutzen.utils :refer [log]]
            [schutzen.physics.core :as physics]
            [schutzen.graphics.core :as graphics]))

;; Defines the a unique id for each actor on the screen. Increments
;; when one is assigned.
(defonce id-count (atom 0))

;; Most basic representation for an object being displayed on the
;; screen.
(defrecord Actor 
    [name
     id
     type
     physics
     graphics
     collision
     state])

(defn create 
  "Create actor with a set of defaults provided

  Keyword Arguments:

  name -- the name of the actor. This does not need to be unique, as the id is provided

  type -- a keyword corresponding to the type of the actor ex. :enemy

  init-img -- refers to the initial image representing this actor"
  [name type & {:keys [mass] :or {mass 1.0}}]
  (let [id (swap! id-count inc)]
    (map->Actor
     {:name name
      :id id
      :type type
      :physics (physics/create :mass mass)
      :graphics (atom nil)
      :collision (atom nil)
      :state (atom {})})))
