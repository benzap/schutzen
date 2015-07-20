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

(defn ^:export add-score! [amt]
  (swap! game update-in [:score] + amt))

(defn ^:export add-life! [amt]
  (swap! game update-in [:life] + amt))

(defn ^:export remove-life! [amt]
  (swap! game update-in [:life] - amt))

(defn ^:export add-bomb! [amt]
  (swap! game update-in [:bomb] + amt))

(defn ^:export remove-bomb! [amt]
  (swap! game update-in [:bomb] - amt))
