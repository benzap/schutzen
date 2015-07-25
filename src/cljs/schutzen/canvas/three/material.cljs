(ns schutzen.canvas.three.material
  "Wrapper around three.js materials"
  (:require [schutzen.canvas.three.object :as object]
            [schutzen.canvas.three.texture :as texture]))

(defn create-sprite [& {:keys [map]}]
  (THREE.SpriteMaterial. #js {:map map}))

(defn create-sprite-from-asset [name]
  (let [texture (texture/create-from-asset name)]
    (create-sprite :map texture)))
