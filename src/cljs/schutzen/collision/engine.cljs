(ns schutzen.collision.engine
  "The collision engine, which compares actors to throw collision
  events"
  (:require [schutzen.utils :refer [log]]
            [schutzen.collision.core]
            [schutzen.collision.partitioning :as partitioning]))

(defn actors-collided? [first-actor second-actor]
  (schutzen.collision.core/is-collision? first-actor second-actor))

(defn process-on-collision [first-actor second-actor]
  (log "Actors Collided" first-actor second-actor))

(defn process-off-collision [first-actor second-actor])

(defn is-landscape-actor? [first-actor]
  (let [actor-type (-> first-actor :name)]
    (= actor-type "landscape")))

(defn has-collision-component? [actor]
  (not= (-> actor :collision deref) nil))

(defn should-check-collision? 
  "Filters out actors that we shouldn't check collisions against"
  [first-actor second-actor]
  (and
   (not= first-actor second-actor)
   (not (is-landscape-actor? first-actor))
   ;;(has-collision-component? first-actor)
   ;;(has-collision-component? second-actor)
   ))

(defn process-collisions [actors delta-sec]
  (let [num-actors (dec (count actors))]
    (loop [ai1 0
           ai2 0]
      (let [first-actor (actors ai1)
            second-actor (actors ai2)]

        ;; Check Collision
        (when (should-check-collision? first-actor second-actor)
          (if (actors-collided? first-actor second-actor)
            (process-on-collision first-actor second-actor)))
        
        ;; Loop
        (cond
          (and (< ai1 num-actors)
               (>= ai2 num-actors))
          (recur (inc ai1) 0)
          
          (and (<= ai1 num-actors)
               (< ai2 num-actors))
          (recur ai1 (inc ai2))

          )))))

(defn run-engine [actors delta-sec]
  (doseq [actor-partition (partitioning/spatial-partition-x actors)]
    (process-collisions actor-partition delta-sec))
  )
