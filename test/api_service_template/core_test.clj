(ns api-service-template.core-test
  (:require  [clojure.test :refer :all]
             [cheshire.core :as cc]
             [clj-http.client :as http]
             [api-service-template.core :as rc]))


(def service-port (atom nil))
(def server-system (atom nil))
(def service-url (atom nil))


(defn test-setup
  []
  (let [configs {:host "localhost"}
        random-service-port (+ 9099
                               (rand-int 1000))
        system (-> configs
                   (assoc :port random-service-port)
                   rc/construct-system
                   rc/start-system)]
    (reset! service-port random-service-port)
    (reset! server-system system)
    (reset! service-url (str "http://localhost:" @service-port))))


(defn test-cleanup
  []
  (rc/stop-system @server-system)
  (reset! server-system nil))


(defn once-fixture
  [tests]
  (test-setup)
  (tests)
  (test-cleanup))


(defn each-fixture
  [tests]
  (tests))


(use-fixtures :once once-fixture)
(use-fixtures :each each-fixture)


(deftest test-ping-route
  (testing "/ping route"
    (let [result (http/get (str @service-url "/ping"))
          body (cc/parse-string (:body result) true)]
      (is (= (:status result)
             200)
          "Response status is 200.")
      (is (= (:ping body)
             "PONG")
          "Response contains PONG."))))

(deftest test-hello-route
  (testing "/hello route"
    (let [result (http/get (str @service-url "/hello"))
          body (cc/parse-string (:body result) true)]
      (is (= (:status result)
             200)
          "Response status is 200.")
      (is (= (:message body)
             "Hello World from Clojure.")
          "Response contains hello message."))))


(deftest test-invalid-route
  (testing "/test route"
    (let [result (http/get (str @service-url "/test")
                           {:throw-exceptions false})]
      (is (= (:status result)
             404)
          "Response status is 404 as /test route is not defined."))))
