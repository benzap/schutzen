(ns schutzen.canvas.three.texture
  (:require [schutzen.canvas.three.object :as object]
            [schuzten.assets :as assets]))

(defn create [image]
  (THREE.Texture. image))

(defn create-from-asset 
  "loads a texture from an asset. note that this won't work correctly
  if the assets haven't been fully loaded yet."
  [name]
  (let [image (assets/get-image name)]
    (create image)))

