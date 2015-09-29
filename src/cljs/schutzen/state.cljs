(ns schutzen.state
  "Contains the application game state, along with helper functions
  for manipulating it."
  (:require [schutzen.utils :refer [log]]))

;; Constants

;; Dimensions of the screen
;; |__|_|__|
;; 0  3 4  7
(def screen-width 640)
(def screen-height 480)
(def screen-offset (* screen-width 3))
(def viewport-width (* screen-width 7))

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
    :life 5
    :bombs 3
    :level 0
    :actors []
    :viewport {:left 0}
    :spacelayers (atom [])
    })
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
  (swap! game update-in [:bombs] + amt))

(defn ^:export remove-bomb! [amt]
  (swap! game update-in [:bombs] - amt))

(defn transform-viewport! [f & args]
  (apply swap! game update-in [:viewport] f args))

(defn transform-actors! [f & args]
  (apply swap! game update-in [:actors] f args))

(defn ^:export add-actor! [actor]
  (transform-actors! conj actor))
