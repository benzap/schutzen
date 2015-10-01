(ns schutzen.game.logic.ship-actions
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [ax= ay= aa a== ax ay]]
            [schutzen.game.actor-manager :as actor-manager]
            [schutzen.event]
            [schutzen.game.logic.ship-projectile-actions :as ship-projectile-actions]))

(defn fire-projectile [actor]
  (let [position (-> actor :physics :position)
        x-offset 50
        y-offset 3
        x-pos (ax position)
        y-pos (ay position)
        ship-direction (-> actor :state deref :ship-direction)]
    (condp = ship-direction
      :left
      (ship-projectile-actions/fire-ship-projectile [(- x-pos x-offset) (- y-pos y-offset)] :left)
      :right
      (ship-projectile-actions/fire-ship-projectile [(+ x-pos x-offset) (- y-pos y-offset)] :right))
    (on-timeout 
     1
     (deallocate-actor! :ship-projectile))
    
    ))
