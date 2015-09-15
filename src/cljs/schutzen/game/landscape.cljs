(ns schutzen.game.landscape
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.landscape]))

(defn generate-landscape-actors [color width]
  (let [landscape-listing
        (schutzen.graphics.landscape/generate-landscape-listing)]
    (map (fn [{:keys [path-listing x-position]}]
           (schutzen.actors.landscape/create 
            path-listing 
            :color color 
            :width width
            :x-position x-position)
           ) landscape-listing)))
