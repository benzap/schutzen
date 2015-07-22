(ns schutzen.canvas.three.object
  "Includes protocols for manipulating the THREE.Object3D types"
  (:require [schutzen.utils :refer [log]]))

(defprotocol ITranslate
  (translate! [this x y z]))

(extend-protocol ITranslate
  THREE.Object3D
  (translate! [this x y z]
    (doto this
      (.translateX x)
      (.translateY y)
      (.translateZ z))))

(defprotocol IRotate
  (rotate-z! [this z]))

(extend-protocol IRotate
  THREE.Object3D
  (rotate-z! [this z]
    (let [z-axis (THREE.Vector3. 0 0 1)]
      (doto this
        (.rotateOnAxis z-axis z)))))

(defprotocol IScale
  (scale! [this x y z]))

(extend-protocol IScale
  THREE.Object3D
  (scale! [this x y z]
    (aset this "scale" (THREE.Vector3. x y z))
    (.updateMatrix this)))

(defprotocol IChildLookup
  (get-by-name [this name]))

(extend-protocol IChildLookup
  THREE.Object3D
  (get-by-name [this name]
    (.getObjectByName this)))
