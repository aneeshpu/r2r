(defproject r2r "0.1.0-SNAPSHOT"
  :description "A webapp to store things that I learn"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [ring.velocity "0.1.2"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring-mock "0.1.5"]
                 [com.novemberain/monger "1.7.0"]
                 [ring/ring-json "0.2.0"]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler r2r.handler/app}
  :main ^:skip-aot r2r.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
