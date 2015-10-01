(ns schutzen.game.actor-manager
  "Used to instantiate and keep track of all of the actors, and
  re-uses actors to be placed back in the actor pool."
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.ship]
            [schutzen.actors.ship-projectile]
            [schutzen.actors.mutant]
            [schutzen.actors.projectile]
            [schutzen.state :as state]))

;; Includes link to each actor type
(def actor-registry
  {
   :mutant 
   {:creation-fcn
    [schutzen.actors.mutant/create]
    :reservation 0
    }
   :ship
   {:creation-fcn
    [schutzen.actors.ship/create]
    :reservation 1
    }
   :ship-projectile
   {:creation-fcn 
    [schutzen.actors.ship-projectile/create]
    :reservation 10
    }
   :basic-projectile
   {:creation-fcn 
    [schutzen.actors.projectile/create]
    :reservation 20
    }
   })

(def manager 
  (atom {}))

(defn init-manager []
  (doseq [[actor-name 
           {[creation-fcn & args] :creation-fcn
            reservation :reservation}]
          actor-registry]
    (swap! manager assoc actor-name
           {:allocated #{}
            :deallocated (into #{} (mapv #(apply creation-fcn args) (range reservation)))})
    ))

(defn allocate-actor! [actor-name]
  (let [actor-list (-> @manager actor-name :deallocated)]
    (if-not (empty? actor-list)
      (let [[actor & actor-list] actor-list]
        (swap! manager assoc-in [actor-name :deallocated] actor-list)
        (swap! manager update-in [actor-name :allocated] conj actor)
        actor
        )
      (let [[creation-fcn & args] (-> actor-registry actor-name :creation-fcn)
            actor (apply creation-fcn args)]
        (swap! manager update-in [actor-name :allocated] conj actor)
        actor
        )
      )))

(defn deallocate-actor! [actor-name actor]
  (swap! manager update-in [actor-name :allocated] disj actor)
  (swap! manager update-in [actor-name :deallocated] conj actor)
  actor
  )
