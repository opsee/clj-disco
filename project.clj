(defproject clj-disco "0.0.1"
  :description "A client library for registrator-backed service discovery"
  :url "https://github.com/opsee/clj-disco"
  :license "MIT"
  
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [verschlimmbesserung "0.1.2"]
                 [org.clojure/tools.logging "0.3.1"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}}
  :deploy-repositories {"clojars-https" {:url      "https://clojars.org/repo"
                                         :username :env/clojars_user
                                         :password :env/clojars_pass}})
