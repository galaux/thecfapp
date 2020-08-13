(ns thecfapp.db.client
  (:require
   [datomic.client.api :as d]
   [integrant.core :as ig]))

(defmethod ig/init-key :thecfapp.db/client
  [id datomic-conf]
  (d/client datomic-conf))


