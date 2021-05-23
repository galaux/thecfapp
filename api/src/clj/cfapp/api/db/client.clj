(ns cfapp.api.db.client
  (:require
    [datomic.client.api :as d]
    [integrant.core :as ig]
    [clojure.tools.logging :as log]))

(defmethod ig/init-key :cfapp.api.db/client
  [_ datomic-conf]
  (log/info "starting …")
  (let [client (d/client datomic-conf)]
    (log/info "done.")
    client))

(defmethod ig/halt-key! :cfapp.api.db/client
  [_ _]
  (log/info "stopping …"))
