(ns schutzen.event
  "Contains wrappers around the window for keyboard events. Provides a
  simpler system for tracking when the key and pressed and
  depressed. Could be expanded on to track key combinations.
  
  Examples:

  (on-keydown 
   :space
   (log \"Hello dry skin build up!\"))

  (on-keyup
   :space
   (log \"Goodbye spacebar!\"))

  "
  (:require [schutzen.utils :refer [log]])
  (:require-macros [schutzen.event
                    :refer [on-keydown on-keyup on-timeout]]))

;;
;; Keyboard Events
;;

(def keycode-map
  {:enter 13
   :left 37
   :up 38
   :right 39
   :down 40
   :space 32
   :a 65 :b 66 :c 67
   :d 68 :e 69 :f 70
   :g 71 :h 72 :i 73
   :j 74 :k 75 :l 76
   :m 77 :n 78 :o 79
   :p 80 :q 81 :r 82
   :s 83 :t 84 :u 85
   :v 86 :w 87 :x 88
   :y 89 :z 90})

(def keys-pressed (atom #{}))

(defn set-on-keydown [key callback]
  (let [keycode (keycode-map key)
        callback-wrapper
        (fn [event]
          (let [event-keycode (.-keyCode event)]
            (when (and (= keycode event-keycode)
                       (not (key @keys-pressed)))
              (callback)
              (swap! keys-pressed conj key))))
        ;;need to track when the key goes up, so we can remove it from
        ;;the keys-pressed set
        keyup-listener
        (fn [event]
          (let [event-keycode (.-keyCode event)]
            (when (= keycode event-keycode)
              (swap! keys-pressed disj key))))]
    (.addEventListener js/document "keydown" callback-wrapper)
    (.addEventListener js/document "keyup" keyup-listener)))

(defn set-on-keyup [key callback]
  (let [keycode (keycode-map key)
        callback-wrapper
        (fn [event]
          (let [event-keycode (.-keyCode event)]
            (when (= keycode event-keycode)
              (callback))))]
    (.addEventListener js/document "keyup" callback-wrapper)))

;;
;; Timed Events
;;


(def timed-events (atom []))
(def timed-id (atom 0))
(def timer-clock (atom 0.0))


(defn add-timed-event! [time func]
  (swap! timed-events conj {:id @timed-id
                            :func func
                            :time (+ @timer-clock time)})
  (swap! timed-id inc))

(defn remove-timed-event! [id]
  (let [events @timed-events]
    (reset! timed-events (filter #(not= id (% :id)) events))))

(defn set-on-timeout [time-sec callback]
  (add-timed-event! time-sec callback)
  ;;(.setTimeout js/window callback (* time-sec 1000))
  )

(defn run-timer-system [delta]
  (swap! timer-clock + delta)
  (doseq [{:keys [id func time]} @timed-events]
    (when (> @timer-clock time)
      (func)
      (remove-timed-event! id))
    ))

;;(on-timeout 3 (log "ding!"))
