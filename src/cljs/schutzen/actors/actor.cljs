(ns schutzen.actors.actor
  "Is the simplest representation of something you would draw on the
  main scene canvas. Certain aspects of the actor uses mutable values
  in order to be more performant.
  
  Notes:
  
  /nothing yet/

  "
  (:require [schutzen.utils :refer [log]]
            [schutzen.physics.core :as physics]
            [schutzen.graphics.core :as graphics]
            [schutzen.array2 :refer [ax ay]]))

;; Defines the a unique id for each actor on the screen. Increments
;; when one is assigned.
(defonce id-count (atom 0))

;; Used to find the bounds of an actor within the world. This is useful for simple
;; collision detection in spatial partitioning.
(defprotocol IWorldBounded
  (get-world-top-bound [this])
  (get-world-right-bound [this])
  (get-world-bottom-bound [this])
  (get-world-left-bound [this])
  (get-world-center [this])
  )

;; Most basic representation for an object being displayed on the
;; screen.
(defrecord Actor 
    [name
     id
     type
     physics
     graphics
     collision
     state]
  IWorldBounded
  (get-world-top-bound [this]
    (+
     (-> this :physics :position ay)
     (graphics/get-top-bound (-> this :graphics deref))
     ))
  
  (get-world-right-bound [this]
    (+
     (-> this :physics :position ax)
     (graphics/get-right-bound (-> this :graphics deref))
     ))

  (get-world-bottom-bound [this]
    (+
     (-> this :physics :position ay)
     (graphics/get-bottom-bound (-> this :graphics deref))
     ))
  
  (get-world-left-bound [this]
    (+
     (-> this :physics :position ax)
     (graphics/get-left-bound (-> this :graphics deref))
     ))
  (get-world-center [this]
    [
     (/
      (+ (get-world-left-bound this)
         (get-world-right-bound this))
      2)
     (/
      (+ (get-world-top-bound this)
         (get-world-bottom-bound this))
      2)
     ]))

(defn create 
  "Create actor with a set of defaults provided

  Keyword Arguments:

  name -- the name of the actor. This does not need to be unique, as the id is provided

  type -- a keyword corresponding to the type of the actor ex. :enemy

  init-img -- refers to the initial image representing this actor"
  [name type & {:keys [position mass] :or {position [0 0] mass 1.0}}]
  (let [id (swap! id-count inc)]
    (map->Actor
     {:name name
      :id id
      :type type
      :physics (physics/create :position position :mass mass)
      :graphics (atom nil)
      :collision (atom nil)
      :state (atom {})})))
