(ns clj-disco.core-test
  (:require [midje.sweet :refer :all]
            [clj-disco.core :as disco]
            [verschlimmbesserung.core :as etcd]))

(facts "get-service"
  (with-state-changes [(before :facts (disco/with-etcd (etcd/delete-all! (deref disco/client) "/opsee.co/registrator")))]
    (fact "get-service returns nil if no service defined"
      (disco/get-service-endpoint "test-service") => nil)
    (fact "get-service returns a service if one service entry"
      (disco/with-etcd (#'disco/etcd-create "/opsee.co/registrator/test-service/instance1" "foo:123"))
      (disco/get-service-endpoint "test-service") => {:host "foo" :port "123"})
    (fact "get-service returns a service if multiple entries"
      (disco/with-etcd (#'disco/etcd-create "/opsee.co/registrator/test-service/instance1" "foo:123"))
      (disco/with-etcd (#'disco/etcd-create "/opsee.co/registrator/test-service/instance2" "foo:123"))
      (disco/with-etcd (#'disco/etcd-create "/opsee.co/registrator/test-service/instance3" "foo:123"))
      (disco/get-service-endpoint "test-service") => {:host "foo" :port "123"})))