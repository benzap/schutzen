(ns schutzen.graphics.particle
  "Includes the particle engine and particle groups for generating
  different particle effects"
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [aa ac a== a**i v->a a->v ax= ax ay a++]]
            [schutzen.graphics.utils]
            [schutzen.canvas.two.core :as c2d]
            [schutzen.state :as state]
            [schutzen.random :as random]
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
    (a++ velocity (a**i (aa 0 gravity) delta-sec))

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

(defn generate-particle 
  [x y &
   {:keys 
    [velocity-addition
     min-speed
     max-speed
     min-damping
     max-damping
     min-duration
     max-duration
     min-mass
     max-mass
     min-width
     max-width
     color-distribution
     ]}]
  (let [vx (random/pick-value-in-range min-speed max-speed)
        _ (log "vx-1" vx)
        vx (if (random/percent-chance 50) vx (- vx))
        _ (log "vx-2" vx)
        vx (+ vx (first velocity-addition))
        _ (log "vx-3" vx)
        vy (random/pick-value-in-range min-speed max-speed)
        vy (if (random/percent-chance 50) vy (- vy))
        vy (+ vy (second velocity-addition))
        
        damping (random/pick-value-in-range min-damping max-damping)
        duration (random/pick-value-in-range min-duration max-duration)
        mass (random/pick-value-in-range min-mass max-mass)
        width (random/pick-value-in-range min-width max-width)
        color (random/pick-rand-by-dist color-distribution)
        ]
    (create-particle [x y] [vx vy] duration
                     :damping damping
                     :duration duration
                     :mass mass
                     :width width
                     :color color)
  ))

(defn generate-explosion
  "Generates an explosion of particles at the given location."
  [x y & 
   {:keys 
    [velocity-addition
     num-particles
     min-speed
     max-speed
     min-damping
     max-damping
     min-duration
     max-duration
     min-mass
     max-mass
     min-width
     max-width
     color-distribution
     ]
    :or {velocity-addition [0 0]
         num-particles 10
         min-speed 0
         max-speed 400
         min-damping 0.97
         max-damping 1.00
         min-duration 0.5
         max-duration 1.5
         min-mass 0.5
         max-mass 2.0
         min-width 1.0
         max-width 1.5
         color-distribution [[1.0 "#ffffff"]]
         }}]
  (doseq [_ (range num-particles)]
    (state/add-particle!
     (generate-particle 
      x y
      :velocity-addition velocity-addition
      :min-speed min-speed
      :max-speed max-speed
      :min-damping min-damping
      :max-damping max-damping
      :min-duration min-duration
      :max-duration max-duration
      :min-mass min-mass
      :max-mass max-mass
      :min-width min-width
      :max-width max-width
      :color-distribution color-distribution))
    ))
