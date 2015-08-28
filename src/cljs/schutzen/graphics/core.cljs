(ns schutzen.graphics.core
  (:require [schutzen.utils :refer [log]]
            [schutzen.assets :as assets]))

(defrecord Sprite [img])

(defn create-sprite [img]
  (map->Sprite {:img (atom img)}))

(defn set-sprite! [sprite img]
  (reset! (-> sprite :img) img))

(defn set-sprite-with-asset! [sprite asset-name]
  (set-sprite! sprite (assets/get-image asset-name)))

