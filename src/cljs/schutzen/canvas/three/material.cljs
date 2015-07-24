(ns schutzen.canvas.three.material
  "Wrapper around three.js materials"
  (:require [schutzen.canvas.three.object :as object]))

(defn create-sprite [{:keys [map]}]
  (THREE.SpriteMaterial. #js {:map map}))
