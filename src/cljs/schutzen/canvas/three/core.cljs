(ns schutzen.canvas.three.core
  "Contains a three.js instantiation, with functions for drawing
  sprites, and manipulating the scene"
  (:require [schutzen.utils :refer [log]]
            [schutzen.canvas.three.object :as object]))

(defn init-renderer
  "Creates a <div> containing a three.js canvas that appends to the
  current dom element. This represents a three.js canvas"
  [dom]
  (let [width (.-clientWidth dom)
        height (.-clientHeight dom)
        renderer (THREE.WebGLRenderer.)]
    ;;set the size of the renderer to size of our dom element
    (.setSize renderer width height)
    (.appendChild dom (.-domElement renderer))
    renderer))
 
(defn init-scene [] (THREE.Scene.))

(defn init-camera
  "initialize and create camera"
  [fov aspect-ratio near far]
  (THREE.PerspectiveCamera. fov aspect-ratio near far))

(defn init
  "initialize and return renderer, scene, and camera.
  aspect-ratio is consistent at every render size"
  [dom]
  (let [renderer (init-renderer dom)
        scene (init-scene)
        camera (init-camera 45 (/ 4 3) 0.1 1000)]
    (object/translate! camera 0 0 5)
    (.add scene camera)
    ;;(.lookAt camera)
    {:renderer renderer
     :scene scene
     :camera camera}))
