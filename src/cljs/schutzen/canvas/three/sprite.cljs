(ns schutzen.canvas.three.sprite
  "Defined Object3D Sprite. A sprite always faces the camera"
  (:require [schutzen.canvas.three.material :as material]
            ))

(defn create 
  "Creates a sprite with the given material"
  [material]
  (THREE.Sprite. material))

(defn create-with-asset [name]
  (let [sprite-material (material/create-sprite-from-asset name)
        sprite-object (create sprite-material)]
    sprite-object))
 
