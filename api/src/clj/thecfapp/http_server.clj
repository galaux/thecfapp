(ns thecfapp.http-server
  (:require
   [integrant.core :as ig]
   [ring.adapter.jetty :as jetty]
   [clojure.tools.logging :as log]))

(defmethod ig/init-key :thecfapp/http-server
  [_ {:keys [handler] :as opts}]
  (log/info "starting …")
  (jetty/run-jetty handler (-> opts (dissoc :handler) (assoc :join? false))))

(defmethod ig/halt-key! :thecfapp/http-server
  [_ server]
  (log/info "stopping …")
  (.stop server))
