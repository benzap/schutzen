(ns schutzen.collision.event
  (:require [schutzen.utils :refer [log]]))

(defmulti on-collision 
  (fn [first-actor second-actor]
    [(-> first-actor :name) (-> second-actor :name)]))

(defmethod on-collision :default [first-actor second-actor])

(defmulti off-collision
  (fn [first-actor second-actor]
    [(-> first-actor :name) (-> second-actor :name)]))

(defmethod off-collision :default [first-actor second-actor])
