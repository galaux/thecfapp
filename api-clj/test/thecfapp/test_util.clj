(ns thecfapp.test-util
  (:require
   [clojure.data.json :as json]
   [clojure.string :as s]
   [datomic.client.api :as d]
   [integrant.core :as ig]
   [ring.mock.request :refer [body request]]
   [thecfapp.core :as core]
   [thecfapp.db.core :as db])
  (:import java.util.UUID))

(def ^:private fragments-dir "../packages/mobile/src/graphql/fragment")
(def ^:private queries-dir "../packages/mobile/src/pages")

(defn remove-client-fields
  "Remove GraphQL field declared with a `@client` directive.
   The purpose is to turn a fragment which is specific to the mobile project
   (because of this directive) into a valid generic fragment that can be used
   on backend tests.
   For now this function specifically targets this directive but this could be
   extended in the future."
  [s]
  (s/replace s #"([^\s]+\s@client)" ""))

(defn- load-and-clean-one
  [dir file-name]
  (-> (str dir "/" file-name ".graphql")
      slurp
      remove-client-fields))

(defn- load-and-clean-graphql
  [dir gql-element]
  (->> gql-element
       (map (fn [[k v]] [k (load-and-clean-one dir v)]))
       (into {})))

(def fragments
  (load-and-clean-graphql
    fragments-dir
    {:Community "community"
     :CommunityAddress "community-address"
     :CommunityWithAddress "community-with-address"
     :Score "score"
     :ScoreWithBelongsToLoggedUser "score-with-belongs-to-logged-user"
     :User "user"
     :Wod "wod"
     :Workout "workout"}))

(def queries
  (load-and-clean-graphql
    queries-dir
    {:CommunityDetail "community/detail/community-detail"
     :FindCommunities "community/find/find-communities"
     :GetUser "user/profile/user"
     :ListWorkouts "workout/user-workouts"
     :UserCommunities "community/user-communities"
     :UserWods "wod/user-wods"
     :WodWithScores "score/scoreboard/scoreboard"

     :JoinCommunity "community/join-community"
     :LeaveCommunity "community/leave-community"}))

(defn query-with-fragments
  [query-or-key fragment-keys]
  (let [query (if (keyword? query-or-key)
                (get queries query-or-key)
                query-or-key)]
    (->> (map fragments fragment-keys)
         (cons query)
         (s/join "\n"))))

(defn setup
  []
  (let [test-config (-> (core/app-config :test)
                        (update-in [:thecfapp.db/conn :db-name]
                                   #(str % "-test-" (UUID/randomUUID))))
        _ (ig/load-namespaces test-config)
        keys-to-init (-> test-config
                         keys
                         set
                         (disj :thecfapp/http-server))
        test-sys (ig/init test-config keys-to-init)
        conn (-> test-sys :thecfapp.db/conn)
        app (-> test-sys :thecfapp/handler)]
    (db/install-schema conn)
    test-sys))

;; (defmacro with-system
;;   [bindings & body]
;;   (let [system (bindings 1)]
;;     `(do
;;        (println "<<<<< before" ~system)
;;        (let* ~(destructure bindings) ~@body)
;;        (println ">>> after" (-> ) " // " (-> ~system :thecfapp.db/client))
;;        ;; (d/delete-database client {:db-name db-name})
;;        (ig/halt! ~system))))

(defn test-query
  ([query-or-key fragments]
   (test-query query-or-key fragments nil))
  ([query-or-key fragments variables]
   (let [graphql-q (query-with-fragments query-or-key fragments)]
     (-> (request :post "/api/graphql")
         (body (json/write-str {:query graphql-q
                                :variables variables}))))))

(defn domain-add
  [conn tx-data]
  (d/transact conn {:tx-data tx-data}))
