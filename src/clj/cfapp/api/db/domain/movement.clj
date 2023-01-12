(ns cfapp.api.db.domain.movement
  (:require
   [datomic.client.api :as d]))

(defn by-id
  [db id]
  (ffirst
    (d/q '[:find (pull ?e [*])
           :where [?e :movement/id ?id]
           :in $ ?id]
         db
         id)))
