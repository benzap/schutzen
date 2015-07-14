(ns schutzen.scenes.core
  "entry point facade for manipulating each scene"
  (:require [schutzen.display :as display]
            [schutzen.utils :refer [log]]))

(defn rel-scale
  "Represents a factor on the scale of the original game, based on
  screen resolution. The returned value is multiplied by the relative
  scale"
  [& [val]]
  (-> js/document
      (.querySelector ".schutzen-main")
      (.-offsetHeight)
      (/ 480)
      (* (or val 1))))

(defn init-scene-containers
  "Sets up the scene containers, and returns a map of dom containers
  for scene instantiation"
  [dom]
  (let [dom-width (.-clientWidth dom)
        dom-height (.-clientHeight dom)
        screen-dims (display/fix-aspect-and-center
                     dom-width dom-height)
        
        ;;Containers
        main-container (.createElement js/document "div")
        
        ;;top containers
        inner-top-container (.createElement js/document "div")

        top-left-container (.createElement js/document "div")
        top-middle-container(.createElement js/document "div")
        top-right-container (.createElement js/document "div")
        
        ;;bottom container
        inner-bottom-container (.createElement js/document "div")
        
        ]
    ;;place the containers within our provided dom element
    
    ;;style main container and fit to 4:3 aspect ratio
    (.appendChild dom main-container)
    (doto main-container
      (.setAttribute "class" "schutzen-main")
      (aset "style" "position" "absolute")
      (aset "style" "width" (str (:width screen-dims) "px"))
      (aset "style" "height" (str (:height screen-dims) "px"))
      (aset "style" "left" (str (:left screen-dims) "px"))
      (aset "style" "top" (str (:top screen-dims) "px"))
      (aset "style" "backgroundColor" "#000"))

    ;;inner top container
    (.appendChild main-container inner-top-container)
    (let [top-height (/ (:height screen-dims) 7)]
      (doto inner-top-container
        (.setAttribute "class" "schutzen-inner-top")
        (aset "style" "position" "absolute")
        (aset "style" "width" (str (:width screen-dims) "px"))
        ;;makes up a 7th of the screen
        (aset "style" "height" (str top-height "px"))
        (aset "style" "left" "0")
        (aset "style" "right" "0")

        ;;set border-box
        (aset "style" "boxSizing" "border-box")
        (aset "style" "borderBottom" (str (rel-scale 6) "px solid blue"))
        
        ))

    ;;top-left-container
    (.appendChild inner-top-container top-left-container)
    (let [width (* 0.25 (.-offsetWidth inner-top-container))
          height (.-offsetHeight inner-top-container)]
      (doto top-left-container
        (.setAttribute "class" "schutzen-top-left")
        (aset "style" "position" "absolute")
        (aset "style" "width" (str width "px"))
        (aset "style" "height" (str height "px"))

        ;;set border-box
        (aset "style" "boxSizing" "border-box")
        (aset "style" "borderRight" (str (rel-scale 6) "px solid blue"))

        ))

    ;;top-middle-container
    (.appendChild inner-top-container top-middle-container)
    (let [width (* 0.50 (.-offsetWidth inner-top-container))
          height (.-offsetHeight inner-top-container)
          left (/ width 2)]
      (doto top-middle-container
        (.setAttribute "class" "schutzen-top-midde")
        (aset "style" "position" "absolute")
        (aset "style" "width" (str width "px"))
        (aset "style" "height" (str height "px"))
        (aset "style" "left" (str left "px"))

        ;;set border-box
        (aset "style" "boxSizing" "border-box")
        (aset "style" "borderTop" (str (rel-scale 6) "px solid blue"))
        
        ))

    ;;top-right-container
    (.appendChild inner-top-container top-right-container)
    (let [width (* 0.25 (.-offsetWidth inner-top-container))
          height (.-offsetHeight inner-top-container)
          left (* width 3)]
      (doto top-right-container
        (.setAttribute "class" "schutzen-top-right")
        (aset "style" "position" "absolute")
        (aset "style" "width" (str width "px"))
        (aset "style" "height" (str height "px"))
        (aset "style" "left" (str left "px"))

        ;;set border-box
        (aset "style" "boxSizing" "border-box")
        (aset "style" "borderLeft" (str (rel-scale 6) "px solid blue"))
        
        ))

    ;;inner-bottom container
    (.appendChild main-container inner-bottom-container)
    (let [height (* (/ 6 7) (.-offsetHeight main-container))
          top (* (/ 1 7) (.-offsetHeight main-container))]
      (doto inner-bottom-container
        (.setAttribute "class" "schutzen-bottom")
        (aset "style" "position" "absolute")
        (aset "style" "height" (str height "px"))
        (aset "style" "top" (str top "px"))
        (aset "style" "left" 0)
        (aset "style" "right" 0)
        ))

    ;;Map containing our scene containers for scene instantiation
    {:inner-bottom-container inner-bottom-container
     :top-left-container top-left-container
     :top-middle-container top-middle-container}
    ))
