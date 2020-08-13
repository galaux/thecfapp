(ns thecfapp.server
  (:require
   [integrant.core :as ig]
   [com.walmartlabs.lacinia.pedestal :as lp]
   [io.pedestal.http :as http]))

(defmethod ig/init-key :thecfapp/server
  [_ {:keys [schema port]}]
  (-> schema
      (lp/service-map {:graphiql true
                       :port (or port 8888)})
      http/create-server
      http/start))

(defmethod ig/halt-key! :thecfapp/server [_ server] (http/stop server))
