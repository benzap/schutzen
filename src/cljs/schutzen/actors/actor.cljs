(ns schutzen.actors.actor
  "An actor is an Object3D instance, or derivative, which contains properties
  obtained through the IActor protocol"
  (:require [schutzen.canvas.three.object :as object]))

(defprotocol IActor
  (set-prop! [_ name value])
  (get-prop [_ name]))

(extend-protocol IActor
  THREE.Object3D
  (set-prop! [this pname value]
    (aset this "userData" (name pname) value))
  (get-prop [this pname]
    (aget this "userData" (name pname))()))

(defn define
  "Define an actor, with a given set of properties"
  [actor-name & kvprops]
  (let [actor (object/create)]
    (doseq [[prop-name prop-key] (partition 2 kvprops)]
      (set-prop! actor prop-name prop-key))
    (aset actor "name" (name actor-name))))
