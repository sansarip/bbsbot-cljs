(ns bbsbot-cljs.config)

(def env js/process.env)

(def server-host (or (.-HOST env) "0.0.0.0"))
(def server-port (or (.-PORT env) 3000))