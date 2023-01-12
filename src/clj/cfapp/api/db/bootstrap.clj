(ns cfapp.api.db.bootstrap
  (:require
   [cfapp.api.db.domain.user :as user]
   [cfapp.api.db.domain.wod :as wod]
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.spec.gen.alpha :as gen]
   [clojure.tools.logging :as log]
   [datomic.client.api :as d])
  (:import
   (java.util UUID)))

(defn- many-to-many-links
  [attr link-count entities-a entities-b]
  (->> (repeatedly (fn []
                     [:db/add
                      (-> (rand-nth entities-a)
                          :db/id)
                      attr
                      (-> (rand-nth entities-b)
                          :db/id)]))
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
  "Due to the generator implementation, it takes a few samples to get
   \"interesting\" so I skipped ahead a bit.
   https://clojure.org/guides/spec#_range_specs_and_generators"
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
                   :user/id    (nth known-user-ids i)
                   :user/email (format know-user-email i)))
               (better-sample user/user-gen (count known-user-ids))))

(defn rand-id
  [_]
  (UUID/randomUUID))

;; TODO we don't need those readers anymore
(defn read-entities
  [e]
  (edn/read-string
    {:readers {'rand-id rand-id}}
    e))

(def slurp-edn (comp read-entities slurp io/resource))

(defn gen-communities
  []
  ;; (-> (better-sample community/community-gen 10)
  ;;     with-tmp-db-ids)
  (slurp-edn "communities.edn"))

;; (defn gen-scores
;;   []
;;   (-> (gen/sample score/score-gen 300)
;;       with-tmp-db-ids))

(defn gen-users
  []
  (-> (concat (better-sample user/user-gen 40)
              (gen-known-users))
      with-tmp-db-ids))

(defn gen-wods
  []
  (-> (better-sample wod/wod-gen 100)
      with-tmp-db-ids))

(defn gen-workouts
  []
  ;; (-> (better-sample workout/workout-gen 100)
  ;;     with-tmp-db-ids))
  (->> (concat
         (slurp-edn "workouts/named.edn")
         (slurp-edn "workouts/custom.edn"))
       vals
       (map :db)
       (map #(assoc % :workout/id (UUID/randomUUID)))
       with-tmp-db-ids))

;; TODO this might be the reason why starting the app looks like its pausing
;; for some seconds
(def movements (slurp-edn "movements.edn"))

(defn gen-txes
  []
  (let [movements   movements
        communities (gen-communities)
        users       (gen-users)
        wods        (gen-wods)
        workouts    (gen-workouts)]
    (concat movements
            communities
            ;; scores
            users
            workouts
            wods
            (many-to-many-links :community.privs/member 50 users communities)
            (compulsory-1-to-1-links :wod/community wods communities)
            (compulsory-1-to-1-links :wod/workout wods workouts))))
           ;; TODO improve this because it currently makes users do wods
           ;; for communities they might not belong to
           ;; (compulsory-1-to-1-links :score/user scores users)
           ;; (compulsory-1-to-1-links :score/wod scores wods))))

(defn go
  [conn]
  (log/info "DB empty: bootstrapping some fake data")
  (d/transact conn {:tx-data (gen-txes)}))
