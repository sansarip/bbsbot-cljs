(ns bbsbot-cljs.routes
  (:require
    [reitit.coercion.spec :as c]
    [bbsbot-cljs.handlers :as handlers]
    [bbsbot-cljs.twitch.handlers :as twitch-handlers]))

(def routes
  [""
   {:swagger  {:info {:title       "bbsbot API swagger json"
                      :version     "0.1.0"}}
    :coercion c/coercion}
   ["/swagger.json"
    {:get {:no-doc  true
           :handler handlers/swagger-json}}]
   ["/twitch"
    ["/webhooks" {:get {:parameters {:query {:hub.challenge string?}}
                        :handler    twitch-handlers/challenge}}]]])

