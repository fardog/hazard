(defproject hazard "0.2.0-SNAPSHOT"
  :description "Get random words, with optional length requirements; for Clojure/ClojureScript"
  :url "https://github.com/fardog/hazard"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]]
  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-doo "0.1.6-SNAPSHOT"]]
  :cljsbuild {
              :test-commands {"default" ["lein" "doo" "phantom" "test" "once"]}
              :builds {:test {:source-paths ["src" "test"]
                              :compiler {:output-to "target/hazard-test.js"
                                         :main hazard.runner
                                         :optimizations :none}}
                       :dev {:source-paths ["src"]
                             :compiler {:output-to "target/hazard-debug.js"
                                        :optimizations :whitespace
                                        :pretty-print true}}}})
