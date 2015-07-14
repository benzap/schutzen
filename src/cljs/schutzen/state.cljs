(ns schutzen.state
  "Contains the application game state, along with helper functions
  for manipulating it.")

(defonce game-state
  (atom {}))

(defn init []
  (reset! game-state
   {:score 0
    :life 3
    :bombs 3
    :level 0
    :actors []}))

