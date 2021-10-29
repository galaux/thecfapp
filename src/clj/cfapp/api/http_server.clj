(ns cfapp.api.http-server
  (:require
   [integrant.core :as ig]
   [ring.adapter.jetty :as jetty]
   [clojure.tools.logging :as log]))

(defmethod ig/init-key :cfapp.api/http-server
  [_ {:keys [handler] :as opts}]
  (log/info "starting …")
  (jetty/run-jetty handler
                   (-> opts
                       (dissoc :handler)
                       (assoc :join? false))))

(defmethod ig/halt-key! :cfapp.api/http-server
  [_ server]
  (log/info "stopping …")
  (.stop server))
