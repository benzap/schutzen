(ns schutzen.actors.landscape
  "Represents a segment of landscape which serves as a prop for humans
  to stand on."
  (:require [schutzen.utils :refer [log]]
            [schutzen.actors.actor :as actor]
            [schutzen.graphics.core :as graphics]
            [schutzen.collision.core :as collision]
            [schutzen.array2 :refer [ax=]]
            [schutzen.collision.landscape]
            ))

(defn create
  "Create the landscape segment actor prop
  
  Keyword Arguments:
  
  path-listing -- list or vector of path x,y points

  color -- color to apply to the path representing the landscape

  width -- width of the path representing the landscape

  x-position -- the starting x position of the landscape segment
  "
  [path-listing & 
   {:keys [color width x-position] 
    :or {color "#FFFFFF"
         width 5
         x-position 0}}]
  (let [landscape-actor (actor/create "landscape" :prop :position [x-position 0])]
    ;; Apply graphics to landscape
    (reset! (-> landscape-actor :graphics)
            (graphics/create-landscape-segment 
             path-listing 
             :color color 
             :width width))
    (reset! (-> landscape-actor :collision)
            (schutzen.collision.landscape/create-landscape-bound))
    landscape-actor
    ))
