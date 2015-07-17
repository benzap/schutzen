(ns schutzen.assets
  "Contains mutable values for storing assets. This uses the
  THREE.ImageLoader, and provides core.async channels for full load,
  progress, and failure."
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [schutzen.utils :refer [log]]
            [schutzen.state :as state]
            [cljs.core.async
             :as async
             :refer [put! chan <! close! pipe]]))

(def asset-images
  (atom
   ;;Image Assets
   {:dot {:url "dot.png"}
    

    }))

(defn download-image
  "Downloads the image from the given url"
  [url]
  (let [image-chan (chan)
        loader (THREE.ImageLoader.)]
    (.load loader url
           ;; Success Function
           (fn [image]
             (log "Downloaded <--" url)
             (put! image-chan image))

           ;; Progress Function
           (fn [xhr]
             (let [loaded (.-loaded xhr)
                   total (.-total xhr)]
               (log url " - " (/ loaded (* 100 total)) "% Loaded")))

           ;; Error Function
           (fn [xhr]
             (log "Failed Download x--" url)
             (close! image-chan)))
    image-chan))

(defn load-images
  "Retrieves the image assets from the server"
  [root-path]
  (doseq [[name data] @asset-images]
    (let [url (:url data)
          full-path (str root-path "/" url)]
      (go
        (let [image-chan (download-image full-path)
              image (<! image-chan)]
          (swap! asset-images assoc-in [name :image] image)
          )))))

(defn get-image
  "Retrieve an image asset by the given keyword name provided in the
  asset-images dictionary. If the file doesn't exist in the assets,
  the file is downloaded."
  [name]
  (let [out (chan)
        image (-> @asset-images name :image)]
    (if image
      (put! out image)
      (let [assets-path (-> @state/app :assets-path)
            url (-> @asset-images name :url)
            full-path (str assets-path "/" url)]
        (pipe (download-image full-path) out))
  )))
