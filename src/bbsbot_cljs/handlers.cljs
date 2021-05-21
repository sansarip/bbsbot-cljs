(ns bbsbot-cljs.handlers
  (:require [reitit.swagger :as swagger]
            [macchiato.util.response :as mur]))

(def swagger* (swagger/create-swagger-handler))

(defn swagger-json [req res _]
  ((swagger/create-swagger-handler)
   req
   #(res (mur/content-type % "application/json"))
   _))