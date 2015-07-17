(ns schutzen.state
  "Contains the application game state, along with helper functions
  for manipulating it.")

;; Game State
(defonce game
  (atom {}))

;; Application State
(defonce app
  (atom {}))

(defn init
  "initialize the default values"
  []
  (reset!
   game
   {:score 0
    :life 3
    :bombs 3
    :level 0
    :actors []})
  (reset!
   app
   {:assets-path "/resources/public"}))
