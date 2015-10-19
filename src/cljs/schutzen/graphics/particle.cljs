(ns schutzen.graphics.particle
  (:require [schutzen.utils :refer [log]]))

(defrecord Particle [position velocity duration])

(defn create-particle [position velocity duration])
