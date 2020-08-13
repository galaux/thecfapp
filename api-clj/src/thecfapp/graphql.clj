(ns thecfapp.graphql
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [com.walmartlabs.lacinia :as lacinia]
   [com.walmartlabs.lacinia.schema :as schema]
   [com.walmartlabs.lacinia.util
    :refer
    [attach-resolvers attach-scalar-transformers]]
   [integrant.core :as ig]
   [thecfapp.domain.community :as community]
   [thecfapp.domain.score :as score]
   [thecfapp.domain.user :as user]
   [thecfapp.domain.wod :as wod]
   [thecfapp.domain.workout :as workout])
  (:import
   java.text.SimpleDateFormat
   java.util.UUID))

;; (def ^:private add-community-input-spec
;;   (s/keys :req-un [:graphql.NewCommunityInput/name]))
 
(def ^:private resolver-map
  {:Community/wods wod/by-community-id
   :Community/member-count community/member-count
   :Mutation/join-community community/user-join
   :Mutation/leave-community user/leave-community
   :Mutation/new-community community/add-community
   :Mutation/new-user user/add-user
   :Query/find-communities community/find-with-name-like
   :Query/get-community community/get-community
   :Query/get-user user/get-user
   :Query/get-wod wod/get-wod
   :Query/list-communities community/list-communities ;; Not used by mobile
   :Query/list-users user/list-users                  ;; Not used by mobile
   :Query/list-workouts workout/list-workouts
   :Query/user-communities community/user-communities
   :Query/user-wods wod/by-user-id
   :Query/user-workouts workout/by-user-id            ;; Not used by mobile
   :Score/user user/by-score-id
   :Score/wod wod/by-score-id
   :User/communities community/user->communities
   :Wod/community community/by-wod-id
   :Wod/scores score/by-wod-id
   :Wod/workout workout/by-wod-id})

(def date-formatter
  (SimpleDateFormat. "yyyy-MM-dd"))

(defn string->uuid
  [s]
  (try
    (UUID/fromString s)
    (catch IllegalArgumentException e
      ;; FIXME This Lacinia fn is deprecated
      (schema/coercion-failure "Cannot coerce UUID from input"))))

(defn string->date
  [s]
  ;; FIXME shouldn't we do something instead of silently failing?
  (when (string? s)
    (try
      (.parse date-formatter s)
      ;; FIXME shouldn't we do something instead of silently failing?
      (catch Throwable _
        nil))))

(defn date->string
  [d]
  (try
    (.format date-formatter d)
    (catch Throwable _
      ;; FIXME shouldn't we do something instead of silently failing?
      nil)))

(def transformer-map
  {:parse-date string->date
   :serialize-date date->string
   :parse-uuid string->uuid
   :serialize-uuid #(.toString %)})

(defmethod ig/init-key :thecfapp.graphql/compiled-schema
  [_ _]
  (-> "graphql/schema.edn"
      io/resource
      slurp
      edn/read-string
      (attach-resolvers resolver-map)
      ;; TODO use https://github.com/workframers/stillsuit ?
      (attach-scalar-transformers transformer-map)
      schema/compile))

(defmethod ig/init-key :thecfapp.graphql/execute-query
  [_ {:keys [conn schema]}]
  (fn [gql-query]
    (let [{:keys [query variables operationName]} gql-query
          context {:conn conn}]
      (lacinia/execute schema
                       query
                       variables
                       context
                       {:operation-name operationName}))))
