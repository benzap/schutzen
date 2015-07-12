(defproject schutzen "0.1.0-SNAPSHOT"
  :description "Defender Clone"
  :url "https://github.com/benzap/schutzen"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3308"]]
  :plugins [[lein-cljsbuild "1.0.6"]]
  :cljsbuild 
  {
   :builds [{:source-paths ["src/cljs"]
             :compiler {:output-to "resources/public/js/schutzen.js" 
                        :output-dir "resources/public/js"
                        :optimizations :whitespace
                        :pretty-print true
                        ;;:source-map "resources/public/js/schutzen.js.map"
                        }}]})
