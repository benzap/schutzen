(ns schutzen.game.shooting
  (:require [schutzen.utils :refer [log]]
            [schutzen.game.actor-manager :as actor-manager]
            [schutzen.event]
            [schutzen.array2 :refer [aa a== ax ay]]
            [schutzen.vector :as v]
            [schutzen.random :as random]
            [schutzen.game.sensing :as sensing])
  (:require-macros [schutzen.event :refer [on-timeout]]))

(defn fire-projectile
  "Fires a projectile from some given position, at a given velocity,
  for a given duration of time."
  [& {:keys [position velocity duration]
      :or {position [0 0]
           velocity [500 0]
           duration 1.0}}]
  (let [projectile (actor-manager/allocate! :basic-projectile)]
    ;; Position
    (a== (-> projectile :physics :position)
         (aa (get position 0)
             (get position 1)))
    
    ;; Velocity
    (a== (-> projectile :physics :velocity)
         (aa (get velocity 0)
             (get velocity 1)))

    ;; Duration
    (on-timeout
     duration
     (actor-manager/deallocate! :basic-projectile projectile))
    ))

(defn fire-from-actor 
  [actor &
   {:keys [velocity duration]
    :or {duration 1.0}}]
  (let [position (-> actor :physics :position)
        x (ax position)
        y (ay position)]
    (fire-projectile 
     :position [x y] 
     :velocity velocity
     :duration duration)
    ))

(defn fire-at
  "Fire a basic projectile from one actor, to another actor. precision
  value is a percentage value, with 0% suggesting a shot that randomly
  shoots within a 90 degree cone, and a precision of 100% being a shot
  directly at it's target (no cone)"
  [from-actor to-actor &
   {:keys [speed duration precision]
    :or {speed 500 duration 1.0 precision 0.7}}]
  (let [unit-vector (sensing/unit-vector-to-actor from-actor to-actor)
        degree-precision (* (- 1.0 precision) 90.0)
        direction (random/skew-vector-direction unit-vector :degrees degree-precision)
        _ (log "before-after" unit-vector direction)
        direction (v/scalar direction speed)
        ]
    (fire-from-actor from-actor :velocity direction :duration duration)
))
