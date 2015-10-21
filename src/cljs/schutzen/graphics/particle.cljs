(ns schutzen.graphics.particle
  "Includes the particle engine and particle groups for generating
  different particle effects"
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [aa ac a== a**i v->a a->v ax= ax ay a++]]
            [schutzen.graphics.utils]
            [schutzen.canvas.two.core :as c2d]
            [schutzen.state :as state]
            ))

(defrecord Particle 
    [position velocity duration mass damping color width])

(defn create-particle 
  [position velocity duration
   & {:keys [mass damping color width]
      :or 
      
      {mass 1.0 
       damping 1.0
       color "#ffffff"
       width 3.0
       duration 1.0
       }}]

   (map->Particle
    {:position (v->a position)
     :velocity (v->a velocity)
     :mass mass
     :damping damping
     :color color
     :width width
     :duration (atom duration)
     }))

(defn animate-particle [particle delta-sec]
  (let [position (-> particle :position)
        velocity (-> particle :velocity)
        mass (-> particle :mass)
        gravity (* mass 100)
        damping (-> particle :damping)
        ]

    ;;acceleration increases velocity by the fraction of time
    (a++ velocity (a**i (ac (aa 0 gravity)) delta-sec))

    ;;velocity changes position by the fraction of time
    (a++ position (a**i (ac velocity) delta-sec))

    ;;apply velocity damping
    (a**i velocity damping)
    
    ))

(defn draw-particle [canvas particle]
  (let [[x y] (a->v (-> particle :position))
        width (-> particle :width)
        color (-> particle :color)]
    (c2d/draw-point canvas 
                    (schutzen.graphics.utils/correct-viewport-offset x)
                    y
                    :width width 
                    :color color)
  ))

(defn correct-particle-position [particle]
  (let [position (-> particle :position)
        xp (ax position)
        ]
    (ax= position (schutzen.graphics.utils/correct-viewport-position xp))
    ))

(defn run-particle-engine [canvas particles delta-sec]
  (doseq [particle particles]
    ;; Reduce the duration of the particle, or remove it
    ;; (log "Particle" particle)
    (if-not (< (-> particle :duration deref) 0)
      (swap! (-> particle :duration) #(- % delta-sec))
      (do
        (state/remove-particle! particle)
        ))
    
    ;; Animate the particle
    (animate-particle particle delta-sec)
    
    ;; Draw the particle
    (correct-particle-position particle)
    (draw-particle canvas particle)
    ))
