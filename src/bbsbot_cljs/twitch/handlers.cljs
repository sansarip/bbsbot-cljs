(ns bbsbot-cljs.twitch.handlers
  (:require [macchiato.util.response :as mur]))

(defn challenge [{{{challenge :hub.challenge} :query} :parameters} res _]
  (res (mur/ok challenge)))
