(ns schutzen.assets
  "Contains mutable values for storing assets. This uses the
  THREE.ImageLoader, and provides core.async channels for full load,
  progress, and failure.")




(def asset-sprites
  (atom {}))

;;2d canvas requires image objects
(def asset-images
  (atom {}))

