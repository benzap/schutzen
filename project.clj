(defproject schutzen "0.1.0-SNAPSHOT"
  :description "Defender Clone"
  :url "https://github.com/benzap/schutzen"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [awohletz/lwjgl "3.0"]]
  :main ^:skip-aot schutzen.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
