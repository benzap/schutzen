(ns schutzen.scenes.scene
  "Includes the base protocol for rendering a scene")

(defprotocol SceneRender
  "A Scene renderer, which renders the contents of the given scene"
  (init-scene
    [_ state]
    "Initialize the current render scene with the given state")
  (run-scene
    [_ state]
    "Start running the scene, with the included state")
  (render-scene
    [_ state delta-ms]
    "A single render frame with the current state, along with the
    delta from the previous render frame") 
  (pause-scene
    [_ state]
    "Used to tell Scene that it is currently paused"))
