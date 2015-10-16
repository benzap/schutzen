(ns schutzen.game.shooting
  (:require [schutzen.utils :refer [log]]
            [schutzen.game.actor-manager :as actor-manager]
            [schutzen.event]
            [schutzen.array2 :refer [aa a== ax ay]])
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
