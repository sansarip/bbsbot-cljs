(ns bbsbot-cljs.core
  (:require [taoensso.timbre :refer [info]]
            [macchiato.server :as http]
            [reitit.ring :as ring]
            [macchiato.middleware.params :as params]
            [reitit.ring.coercion :as rrc]
            [macchiato.middleware.restful-format :as rf]
            [bbsbot-cljs.routes :as routes]
            [bbsbot-cljs.config :as config]))

(defn wrap-coercion-exception
  "Catches potential synchronous coercion exception in middleware chain"
  [handler]
  (fn [request respond _]
    (try
      (handler request respond _)
      (catch :default e
        (let [exception-type (:type (.-data e))]
          (cond
            (= exception-type :reitit.coercion/request-coercion)
            (respond {:status 400
                      :body   {:message "Bad Request"}})

            (= exception-type :reitit.coercion/response-coercion)
            (respond {:status 500
                      :body   {:message "Bad Response"}})
            :else
            (respond {:status 500
                      :body   {:message "Internal server error"}})))))))

(defn wrap-body-to-params
  [handler]
  (fn [request respond raise]
    (handler (-> request
                 (assoc-in [:params :body-params] (:body request))
                 (assoc :body-params (:body request))) respond raise)))

(def app
  (ring/ring-handler
    (ring/router
      [routes/routes]
      {:data {:middleware [params/wrap-params
                           #(rf/wrap-restful-format % {:keywordize? true})
                           wrap-body-to-params
                           wrap-coercion-exception
                           rrc/coerce-request-middleware
                           rrc/coerce-response-middleware]}})
    (ring/create-default-handler)))

(defn start-server! []
  (info "Starting server...")
  (http/start
    {:handler    #'app
     :host       config/server-host
     :port       config/server-port
     :on-success #(info (str "bbsbot started on " config/server-host ":" config/server-port))}))