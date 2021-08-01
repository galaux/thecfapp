(ns cfapp.api.db.bootstrap
  (:require
   [cfapp.api.db.domain.community :as community]
   [cfapp.api.db.domain.score :as score]
   [cfapp.api.db.domain.user :as user]
   [cfapp.api.db.domain.wod :as wod]
   [cfapp.api.db.domain.workout :as workout]
   [clojure.spec.gen.alpha :as gen])
  (:import java.util.UUID))

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

(def know-user-email "g%s+cfapp@alaux.net")

(def known-user-ids
  [#uuid "8c99beba-12da-44c8-aeb2-e44f82ca27cd"
   #uuid "b6b37b12-db24-41c5-ab52-4740dcc3ba0f"
   #uuid "e43f5c9e-1c7b-45e4-906a-25c52dbd2600"
   #uuid "c49d942d-b00c-45aa-8ec5-2c1931b8a006"])

(defn gen-known-users
  "Generate users with known IDs and emails"
  []
  (map-indexed (fn [i base-user]
                (assoc base-user
                       :user/id (nth known-user-ids i)
                       :user/email (format know-user-email i)))
    (better-sample user/user-gen (count known-user-ids))))

(defn bootstrap-tx
  []
  (let [communities (-> (better-sample community/community-gen 10)
                        with-tmp-db-ids)
        scores (-> (gen/sample score/score-gen 300)
                   with-tmp-db-ids)
        users (-> (concat (better-sample user/user-gen 40)
                          (gen-known-users))
                  with-tmp-db-ids)
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
