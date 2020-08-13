(ns thecfapp.http-server
  (:require
   [integrant.core :as ig]
   [ring.adapter.jetty :as jetty]))


;; (defmethod ig/init-key :adapter/jetty [_ {:keys [handler] :as opts}]
;;   (jetty/run-jetty handler (-> opts (dissoc :handler) (assoc :join?)))
;;   false)


(defmethod ig/init-key :thecfapp/http-server
  [_ {:keys [handler] :as opts}]
  (jetty/run-jetty handler (-> opts (dissoc :handler) (assoc :join? false))))

(defmethod ig/halt-key! :thecfapp/http-server
  [_ server]
  (.stop server))
