(ns thecfapp.db.client
  (:require
   [datomic.client.api :as d]
   [integrant.core :as ig]
   [clojure.tools.logging :as log]))

(defmethod ig/init-key :thecfapp.db/client
  [_ datomic-conf]
  (log/info "starting …")
  (let [client (d/client datomic-conf)]
    (log/info "done.")
    client))

(defmethod ig/halt-key! :thecfapp.db/client
  [_ _]
  (log/info "stopping …"))
