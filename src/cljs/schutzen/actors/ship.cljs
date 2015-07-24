(ns schutzen.actors.ship 
  "The main ship actor"
  (:require [schutzen.actors.actor :as actor]))

(defn create 
  "Create the ship actor"
  []
  (actor/define :ship
    :type :player))

