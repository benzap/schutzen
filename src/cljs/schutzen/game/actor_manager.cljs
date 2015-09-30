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
    :reservation 20
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

(defn init-manager [])


(defn allocate-actor [name]
  (when-let []))


