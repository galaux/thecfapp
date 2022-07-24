(ns user
  (:require
   [cfapp.api.core :refer [app-config]]
   ;; [cfapp.api.db.bootstrap :as bootstrap]
   [cfapp.api.db.core :as db]
   [cfapp.api.test-util :as tutil]
   [clojure.tools.namespace.repl :refer [set-refresh-dirs]]
   [datomic.client.api :as d]
   [integrant.core :as ig]
   [integrant.repl :refer [halt go reset reset-all]]
   [integrant.repl.state :as state]
   [cfapp.api.db.domain.workout :as workout]
   [cfapp.api.db.domain.community :as community]))

(add-tap (bound-fn* clojure.pprint/pprint))

;; Work around to enable `cider-ns-refresh` to properly reload code in tests.
;; https://github.com/clojure-emacs/cider/issues/2686#issuecomment-532389499
(set-refresh-dirs "dev" "src" "test")

;; (defn bootstrap-db
;;   []
;;   (let [conn (-> integrant.repl.state/system
;;                  :cfapp.api.db/conn)]
;;     (db/install-schema conn)
;;     (tutil/domain-add conn (bootstrap/gen-txes)))
;;   nil);; Don't waste the REPL with output

(def dev-config
  (app-config :dev))

(defn reload-ig
  []
  (halt)
  (ig/load-namespaces dev-config)
  ((integrant.repl/set-prep! (constantly dev-config))))

;; (reload-ig)

(defn get-conn
  []
  (:cfapp.api.db/conn integrant.repl.state/system))

(defn get-db
  []
  (datomic.client.api/db (get-conn)))

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

(defn remove-db
  []
  (let [client (d/client {:server-type :dev-local
                          :system "cfapp-dev"
                          :storage-dir "/home/miguel/.datomic"})]
    (d/delete-database client {:db-name "domain"})))

(def ^:private q-all-workouts
  '[:find (pull ?workout workout-pull-pattern)
    :in $ workout-pull-pattern
    :where
    [?workout :workout/id]])

(defn all-workouts
  ([db]
   (d/q q-all-workouts db workout/pp-workout))
  ([]
   (all-workouts (get-db))))

(def ^:private q-all-communities
  '[:find (pull ?community pull-pattern)
    :in $ pull-pattern
    :where
    [?community :community/id]])

(def ^:private community-pull-pattern
  [:db/id
   :community/id
   :community/name
   :workout/loop
   [:workout.loop/time-cap]])

(defn all-communities
  []
  (d/q q-all-communities (get-db) community-pull-pattern))

;; Find one element
;;   :find ?year .
;; Find a list
;;   :find [?w ...]

(defn start-api
  []
  (in-ns 'user)
  (reload-ig)
  (go))
