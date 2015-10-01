(ns schutzen.game.logic.ship-projectile-actions
  "Includes actions performed by a ship projectile. Typically this
  involves firing itself at a given position."
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [schutzen.array2 :refer [aa a==]]
            [schutzen.event]
            [schutzen.game.actor-manager :as actor-manager])
  (:require-macros [schutzen.event :refer [on-timeout]]))

(def projectile-speed 2000.0)
(def projectile-timeout 0.35)


(defn fire-ship-projectile [[x y] direction]
  (let [projectile (actor-manager/allocate! :ship-projectile)]
    (a== (-> projectile :physics :position)
         (aa x y))
    (condp = direction
      :left
      (a== (-> projectile :physics :velocity)
           (aa (- projectile-speed) 0))
      :right
      (a== (-> projectile :physics :velocity)
           (aa projectile-speed 0))
      )

    (on-timeout
     projectile-timeout
     (actor-manager/deallocate! :ship-projectile projectile)
     )
    ))
