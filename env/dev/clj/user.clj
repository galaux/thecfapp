(ns user
  (:require
   [cfapp.api.core :as core]
   [cfapp.api.db.bootstrap :as bootstrap]
   [cfapp.api.db.core :as db]
   [cfapp.api.test-util :as tutil]
   [clojure.tools.namespace.repl :refer [set-refresh-dirs]]
   [datomic.client.api :as d]
   [integrant.core :as ig]
   [integrant.repl :refer [halt go reset reset-all]]
   [integrant.repl.state :as state]
   [cfapp.api.db.domain.workout :as workout]))

(add-tap (bound-fn* clojure.pprint/pprint))

;; Work around to enable `cider-ns-refresh` to properly reload code in tests.
;; https://github.com/clojure-emacs/cider/issues/2686#issuecomment-532389499
(set-refresh-dirs "dev" "src" "test")

(defn bootstrap-db
  []
  (let [conn (-> integrant.repl.state/system
                 :cfapp.api.db/conn)]
    (db/install-schema conn)
    (tutil/domain-add conn (bootstrap/gen-txes)))
  nil);; Don't waste the REPL with output

(def dev-config
  (core/app-config :dev))

(defn reload-ig
  []
  (halt)
  (ig/load-namespaces dev-config)
  ((integrant.repl/set-prep! (constantly dev-config))))

;; (reload-ig)

(defn get-db
  []
  (-> integrant.repl.state/system
      :cfapp.api.db/conn
      datomic.client.api/db))

(def ^:private q-workout-by-name
  '[:find (pull ?workout workout-pull-pattern)
    :in $ workout-pull-pattern ?name
    :where
    [?workout :workout/name ?name]])

(defn find-workout-by-name
  [name]
  (d/q q-workout-by-name (get-db) workout/pp-workout name))

(def ^:private q-workout-by-id
  '[:find (pull ?workout workout-pull-pattern)
    :in $ workout-pull-pattern ?id
    :where
    [?workout :workout/id ?id]])

(defn find-workout-by-id
  [id]
  (d/q q-workout-by-id (get-db) workout/pp-workout id))
