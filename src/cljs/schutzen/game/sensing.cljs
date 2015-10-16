(ns schutzen.game.sensing
  (:require [schutzen.utils :refer [log]]
            [schutzen.array2 :refer [ax ay]]))

(defn in-proximity? 
  [first-actor second-actor proximity-range]
  (let [pos1 (-> first-actor :physics :position)
        x1 (ax pos1)
        y1 (ay pos1)
        pos2 (-> second-actor :physics :position)
        x2 (ax pos2)
        y2 (ay pos2)

        xt (- x2 x1)
        yt (- y2 y1)]
    (< (+ (* xt xt) (* yt yt)) 
       (* proximity-range proximity-range))
    ))
