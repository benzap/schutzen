(ns schutzen.physics.damping
  "Includes the simplest force generator for damping. This force
  generator isn't an actual force generator, since it cheats by
  affecting the velocity directly"
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [aa a**i ax* ay* ac]]))

(defn apply-damping [actor damping x-axis-only y-axis-only]
  (let [velocity (-> actor :physics :velocity)
        inv-damping (- 1 damping)
        damped-velocity (a**i (ac velocity) inv-damping)]
    (cond
      x-axis-only
      (ax* velocity damping)
      y-axis-only
      (ay* velocity damping)
      :else
      (a**i velocity damping))))

(defn add-damping! 
  "Applying damping to actor"
  [actor damping & {:keys [x-axis-only y-axis-only]
                    :or {x-axis-only false
                         y-axis-only false}}]
  (swap! (-> actor :physics :force-gens) conj 
         {:force-type :damping
          :damping damping
          :x-axis-only x-axis-only
          :y-axis-only y-axis-only}))

