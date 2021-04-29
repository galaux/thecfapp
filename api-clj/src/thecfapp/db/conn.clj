(ns thecfapp.db.conn
  (:require
   [datomic.client.api :as d]
   [integrant.core :as ig]
   [clojure.tools.logging :as log]))

(defmethod ig/init-key :thecfapp.db/conn
  [id {:keys [client db-name]}]
  (log/info "starting …")
  (d/create-database client {:db-name db-name}) ;; FIXME Useful?
  (let [conn (d/connect client {:db-name db-name})]
    (log/info "done.")
    conn))

(defmethod ig/halt-key! :thecfapp.db/conn
  [_ _]
  (log/info "stopping …"))
