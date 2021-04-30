(ns cfapp.api.db.bootstrap
  (:require [clojure.spec.alpha :as s]
            [cfapp.api.domain.community :as community]
            [cfapp.api.domain.score :as score]
            [cfapp.api.domain.user :as user]
            [cfapp.api.domain.wod :as wod]
            [cfapp.api.domain.workout :as workout]
            [clojure.spec.gen.alpha :as gen])
  (:import java.util.UUID))

(def hard-coded-user-id #uuid "df56c5a2-3f0c-459a-b158-d1a999714247")

(defn- many-to-many-links
  [attr link-count entities-a entities-b]
  (->> (repeatedly (fn []
                     [:db/add
                      (-> (rand-nth entities-a) :db/id)
                      attr
                      (-> (rand-nth entities-b) :db/id)]))
       (take link-count)))

(defn- compulsory-1-to-1-links
  [attr entities-a entities-b]
  (map (fn [entity-a]
         [:db/add
          (:db/id entity-a)
          attr
          (:db/id (rand-nth entities-b))])
       entities-a))

(defn- with-tmp-db-ids
  [entities]
  (map #(assoc % :db/id (.toString (UUID/randomUUID)))
       entities))

(defn- better-sample
  [generator gen-count]
  (->> (gen/sample generator (+ gen-count 50))
       (drop 50)))

(defn bootstrap-tx
  []
  (let [communities (-> (better-sample community/community-gen 10)
                        with-tmp-db-ids)
        scores (-> (gen/sample score/score-gen 300)
                   with-tmp-db-ids)
        users* (-> (better-sample user/user-gen 50)
                   with-tmp-db-ids)
        users (-> (into [] users*)
                  (update 0 #(assoc % :user/id hard-coded-user-id)))
        wods (-> (better-sample wod/wod-gen 100)
                 with-tmp-db-ids)
        workouts (-> (better-sample workout/workout-gen 100)
                     with-tmp-db-ids)]
    (concat communities scores users workouts wods
      (many-to-many-links :community.privs/member 50 users communities)
      (compulsory-1-to-1-links :wod/community wods communities)
      (compulsory-1-to-1-links :wod/workout wods workouts)
      ;; TODO improve this because it currently makes users do wods
      ;; for communities they might not belong to
      (compulsory-1-to-1-links :score/user scores users)
      (compulsory-1-to-1-links :score/wod scores wods))))
