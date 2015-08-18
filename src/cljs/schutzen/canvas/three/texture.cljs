(ns schutzen.canvas.three.texture
  (:require [schutzen.canvas.three.object :as object]
            [schutzen.assets :as assets]))

(defn create [image]
  (let [texture (THREE.Texture. image)]
    (aset texture "needsUpdate" true)
    (aset image "onload" (fn [] (aset texture "needsUpdate" true)))
    texture))

(defn create-from-asset 
  "loads a texture from an asset. note that this won't work correctly
  if the assets haven't been fully loaded yet."
  [name]
  (let [image (assets/get-image name)]
    (create image)))

