(ns clj-disco.core
  "All methods return either a value or nil. If unable to connect to Etcd,
  disco will log an error and print a stack trace, then the method will return
  nil. Clients should implement fallback strategies for when there is no value
  returned from service discovery. The services are represented by a
  PersistentHashMap, e.g.:

  (disco/get-service-endpoint \"bartnet-redis\")
  => {:host \"127.0.0.1\" :port \"2379\"}"
  (:require [verschlimmbesserung.core :as etcd]
            [clojure.tools.logging :as log]))

; We'll want to do this better sometime.
(def base-path "/opsee.co/registrator")
(defn service-path [s] (clojure.string/join "/" [base-path, s]))

(def etcd-url
  (str "http://" (get (System/getenv) "ETCD_HOST" "127.0.0.1") ":" (get (System/getenv) "ETCD_PORT" "2379")))

(def client (atom nil))

(defmacro with-etcd [& body]
  `(do
     (if-not (deref client)
       (try
         (reset! client (etcd/connect etcd-url))
         ~@body
         (catch Exception e#
           (log/error "Error communicating with etcd:" e# (.printStackTrace e#))))
       ~@body)))

(defn- etcd-get [path]
  (etcd/get @client path))

(defn- etcd-create [path value]
  (etcd/create! @client path value))

(defn get-service-endpoint
  "Retrieve connection details for a service."
  [service-name]
  (with-etcd
    (let [bartnet-redis (seq (etcd-get (service-path service-name)))]
      (when bartnet-redis
        (let [endpoint (clojure.string/split (last (rand-nth bartnet-redis)) #":")
              host (first endpoint)
              port (last endpoint)]
          {:host host :port port})))))