(ns cfapp.api.domain.workout
  (:require
    [cfapp.api.spec-util :as sutil]
    [cfapp.api.util :refer [camel-case-keys]]
    [clojure.spec.alpha :as s]
    [clojure.spec.gen.alpha :as gen]
    [clojure.string :as str]
    [datomic.client.api :as d]
    [talltale.core :as fake])
  (:import
    java.util.UUID))

(s/def :workout/id :util/uuid)
(s/def :workout/name :util/non-blank-string)
(s/def :workout/description :util/non-blank-string)
(s/def :workout/scoring-type
  #{:workout.scoring-type/duration-ms
    :workout.scoring-type/repetitions})

(defn- gen-workout
  []
  (gen/fmap
    (fn [[words-name description scoring-type]]
      {:workout/id (UUID/randomUUID)
       :workout/name (str/join " " words-name)
       :workout/description description
       :workout/scoring-type scoring-type})
    (gen/tuple
      (gen/vector-distinct sutil/lorem-ipsum-words-gen
                           {:min-elements 2
                            :max-elements 5})
      sutil/lorem-ipsum-text-gen
      (s/gen :workout/scoring-type))))

(s/def ::workout
  (s/with-gen
    (s/keys :req [:workout/id
                  :workout/name
                  :workout/description
                  :workout/scoring-type])
    gen-workout))

(def workout-gen (s/gen ::workout))


(def ^:private base-workout-pull
  [:workout/id
   :workout/name
   :workout/description
   :workout/scoring-type])

(def ^:private find-all-query
  '[:find (pull ?e pull-expr)
    :in $ pull-expr
    :where [?e :workout/id]])

(defn list-workouts
  [{:keys [conn]} _args _value]
  (->> (d/q find-all-query (d/db conn) base-workout-pull)
       flatten
       (map camel-case-keys)))

(def q-by-wod-id
  '[:find (pull ?workout [*])
    :in $ ?wod-id
    :where
    [?wod :wod/id ?wod-id]
    [?wod :wod/workout ?workout]])

(defn by-wod-id
  [{:keys [conn]} _args {:keys [id]}]
  (-> (d/q q-by-wod-id (d/db conn) id)
      ffirst
      camel-case-keys))

(def ^:private q-by-user-id
  '[:find (pull ?workout [*])
    :in $ ?user-id
    :where
    [?user :user/id ?user-id]
    [?score :score/user ?user]
    [?score :score/wod ?wod]
    [?wod :wod/workout ?workout]
    [?workout :workout/id ?workout-id]])

(defn by-user-id
  [{:keys [conn]} {:keys [id]} _value]
  (->> (d/q q-by-user-id (d/db conn) id)
       flatten
       (map camel-case-keys)))

(defn exists?
  [db id]
  (-> (d/pull db [:db/id] [:workout/id id])
      :db/id
      some?))
