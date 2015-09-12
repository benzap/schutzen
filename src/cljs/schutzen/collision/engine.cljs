(ns schutzen.collision.engine
  "The collision engine, which compares actors to throw collision
  events"
  (:require [schutzen.utils :refer [log]]
            [schutzen.collision.core]))

(defn actors-collided? [first-actor second-actor]
  (schutzen.collision.core/is-collision? first-actor second-actor))

(defn process-on-collision [first-actor second-actor]
  (log "Actors Collided" first-actor second-actor))

(defn process-off-collision [first-actor second-actor])

(defn run-engine [actors delta-sec]
  (let [collision-pairs
        (for [first-actor actors
              second-actor actors
              :when (not= first-actor second-actor)]
          [first-actor second-actor])]
    (doseq [[first-actor second-actor] collision-pairs]
      (if (actors-collided? first-actor second-actor)
        (process-on-collision first-actor second-actor)
        (process-off-collision first-actor second-actor)
        ))))
