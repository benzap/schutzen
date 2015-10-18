(ns schutzen.actors.ship 
  "The main ship actor. I'm assuming that only one is being created"
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.assets :as assets]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.array2 :refer [ay= ax=]]
            [schutzen.physics.damping :as damping]
            [schutzen.collision.event]))

;; How fast the ship ascends and descends in elevation
(def ship-elevation-thrust-speed 350.0)

;; How fast the ship accelerates
(def ship-horizontal-thrust-acceleration 1700.0)

;; How fast the ship decelerates
(def ship-horizontal-damping 0.97)

;; Sprites
(def sprite-ship-right (atom nil))
(def sprite-ship-left (atom nil))

;; Asset Initialization. We need to generate the sprites after assets
;; have loaded in the core of the game.
(defn init-sprites []
  (reset! sprite-ship-right
          (graphics/create-sprite (assets/get-image :ship-right)
                                  48 16
                                  :origin [24 8]))
  (reset! sprite-ship-left
          (graphics/create-sprite (assets/get-image :ship-left)
                                  48 16
                                  :origin [24 8]))
  )

(defn create 
  "Create the ship actor"
  []
  (let [ship-actor (actor/create "ship" :player)]
    (reset! (-> ship-actor :graphics) @sprite-ship-right)
    (reset! (-> ship-actor :collision) 
            (collision/create-bounding-box [48 16] :origin [24 8]))
    (damping/add-damping! ship-actor 
                          ship-horizontal-damping 
                          :x-axis-only true)
    (reset! 
     (-> ship-actor :state) 
     {:ship-direction :right
      })
    ship-actor))

(defn move-up [actor]
  (let [velocity (-> actor :physics :velocity)]
    (ay= velocity (- ship-elevation-thrust-speed))))

(defn move-down [actor]
  (let [velocity (-> actor :physics :velocity)]
    (ay= velocity (+ ship-elevation-thrust-speed))
    ))

(defn move-stop [actor]
  (let [velocity (-> actor :physics :velocity)]
    (ay= velocity 0.0)
    ))

(defn thrust-left [actor]
  (let [acceleration (-> actor :physics :acceleration)]
    (ax= acceleration (- ship-horizontal-thrust-acceleration))
    ))

(defn thrust-right [actor]
  (let [acceleration (-> actor :physics :acceleration)]
    (ax= acceleration ship-horizontal-thrust-acceleration)
    ))

(defn thrust [actor]
  (if (= (-> actor :state deref :ship-direction) :right)
    (thrust-right actor)
    (thrust-left actor)))

(defn thrust-stop [actor]
  (let [acceleration (-> actor :physics :acceleration)]
    (ax= acceleration 0.0)
    ))

(defn toggle-ship-direction [actor]
  (let [ship-direction
        (-> actor :state deref :ship-direction)]
    (log "Ship Direction" ship-direction)
    (if (= ship-direction :right)
      (do
        (reset! (-> actor :graphics) @sprite-ship-left)
        (swap! (-> actor :state) 
               assoc :ship-direction :left)
        )
      (do
        (reset! (-> actor :graphics) @sprite-ship-right)
        (swap! (-> actor :state) 
               assoc :ship-direction :right)
        )
      )))

(defn fire-projectile [actor]
  (let [position (-> actor :physics :position)
        ship-direction (-> actor :state deref :ship-direction)]
    
    ))


;; Collision Events
(defmethod schutzen.collision.event/on-collision ["ship" "mutant"]
  [first-actor second-actor]
  (log "Ship Collided with mutant!"))
