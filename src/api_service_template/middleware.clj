(ns api-service-template.middleware
  (:require [api-service-template.http-util :as ahu]
            [clojure.tools.logging :as ctl]))


(defn wrap-exceptions
  [handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception exception
        (ctl/error exception
                   "Internal Server Error")
        (ahu/internal-server-error "Internal Server Error")))))


(defn log-requests
  [handler]
  (fn [req]
    (let [timestamp (System/currentTimeMillis)
          response (handler req)]
      (ctl/info {:status (:status response)
                 :method (:request-method req)
                 :uri (:uri req)
                 :duration (str (- (System/currentTimeMillis)
                                   timestamp)
                                "ms")})
      response)))
