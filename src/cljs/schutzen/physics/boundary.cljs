(ns schutzen.physics.boundary
  (:require [schutzen.utils :refer [log] :as utils]
            [schutzen.actors.actor :refer [get-world-top-bound get-world-bottom-bound]]
            [schutzen.array2 :refer [ax ay ay=]]
            [schutzen.state :as state]))

(defn actor-outside-top-bound? [actor]
  (< (get-world-top-bound actor) 0))

(defn actor-outside-bottom-bound? [actor]
  (> (get-world-bottom-bound actor) state/screen-height))

(defn maintain-actor-bounds 
  "The bounds of the actor are maintained. This is determined by the
  name of the actor stored in the bounded-actors var"
  [actors]
  (doseq [actor actors]
    (when (contains? state/bounded-actors (-> actor :name))
      (cond
        (actor-outside-top-bound? actor)
        (let [vy (ay (-> actor :physics :velocity))]
          (ay= (-> actor :physics :velocity) (utils/clamp-lower vy 0)))
        (actor-outside-bottom-bound? actor)
        (let [vy (ay (-> actor :physics :velocity))]
          (ay= (-> actor :physics :velocity) (utils/clamp-upper vy 0)))
        ))))
