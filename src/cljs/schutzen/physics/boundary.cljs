(ns schutzen.physics.boundary
  (:require [schutzen.utils :refer [log]]
            [schutzen.graphics.core :as graphics]
            [schutzen.array2 :refer [ax ay]]
            [schutzen.state :as state]))

(defn actor-outside-top-bound? [actor]
  (let [position (-> actor :physics :position)
        y (ay position)
        top-bound (graphics/get-top-bound actor)
        ]
    (> (- y top-bound) 0)
    ))

(defn actor-outside-bottom-bound? [actor]
  (let [position (-> actor :physics :position)
        y (ay position)
        bottom-bound (graphics/get-bottom-bound actor)]
    (< (+ y bottom-bound) state/screen-height)
    ))

(defn actor-in-bounds? [actor]
  (let [position (-> actor :physics :position)
        y (ay position)
        top-bound (graphics/get-top-bound actor)
        bottom-bound (graphics/get-bottom-bound actor)]
    (and
     (actor-outside-top-bound? actor)
     (actor-outside-bottom-bound? actor))))
