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
    :particles []
    })
  (reset!
   app
   {:dev-mode? true
    :assets-path "/resources/public"}))

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

(defn remove-actor! [actor]
  (swap! game assoc :actors 
         (filterv #(not= actor %)
                  (-> @game :actors))))

;; actors that need to remain within the bounds of the game area, and
;; can't go past the top and bottom parts of the screen.
(def bounded-actors 
  #{
    "lander"
    "mutant"
    "ship"
    "bomber"
    "pod"
    "baiter"
    "swarmer"
    })
  
(defn transform-particles! [f & args]
  (apply swap! game update-in [:particles] f args))

(defn ^:export add-particle! [particle]
  (transform-particles! conj particle))

(defn remove-particle! [particle]
  (swap! game assoc :particles 
         (filterv #(not= particle %)
                  (-> @game :particles))))
