(ns schutzen.game.logic.ai
  (:require [schutzen.utils :refer [log]]))

(defn get-actor-logic-state
  "gets the logic state from the actor"
  [actor]
  (-> actor :state deref :logic))

(defmulti trigger-actor-state 
  (fn [actor]
    [(-> actor :name)
     (-> actor :state deref :logic)]
    ))

(defmethod trigger-actor-state :default
  [actor]
  nil)

(defn init-actor-logic 
  "Prepares the actor for ai logic"
  [actor init-state]
  (swap! (-> actor :state) assoc :logic init-state)
  (swap! (-> actor :state) assoc :logic-timer 0))

(defn transition-state! [actor state & args]
  (swap! (-> actor :state) assoc :logic state)
  (swap! (-> actor :state) assoc :logic-args args))

(defn set-state-timer! 
  "Used to time ai, and can be checked against for when it's <0 to
  trigger timed state transitions. The value on :logic-timer is slowly
  decremented with respect to the delta-sec between each animation
  frame."
  [actor time-sec]
  (swap! (-> actor :state) assoc :logic-timer time-sec))

(defn decrement-state-timer! [actor time-sec]
  (when-let [current-time (-> actor :state deref :logic-timer)]
    (swap! (-> actor :state) assoc :logic-timer (- current-time time-sec))))

(defn state-timer-finished? [actor]
  (< (-> actor :state deref :logic-timer) 0))

(defn get-state-arguments [actor]
  (-> actor :state deref :logic-args))
