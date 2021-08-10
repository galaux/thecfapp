(ns cfapp.api.db.domain.community
  (:require
   [cfapp.api.db.domain.user :as user]
   [cfapp.api.util :refer [anomaly anomaly->error]]
   [cfapp.api.spec-util :as sutil]
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [clojure.string :as str]
   [cognitect.anomalies :as anomalies]
   [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
   [datomic.client.api :as d]
   [talltale.core :as fake])
  (:import
   java.util.UUID))

(s/def :community/id :util/uuid)
(s/def :community/name :util/non-blank-string)
(s/def :community.address/street string?)
(s/def :community.address/zip-code string?)
(s/def :community.address/city string?)
(s/def :community.address/country string?)

(defn- gen-community
  []
  (gen/fmap
    (fn [{:keys [company-name address]}]
      (cond->
        {:community/id   (UUID/randomUUID)
         :community/name (->> [company-name "Crossfit" (:city address)]
                              shuffle
                              (str/join " "))}
        (zero? (rand-int 3)) (assoc :community.address/street (:street address))
        (zero? (rand-int 3)) (assoc :community.address/city (:city address))
        (zero? (rand-int 3)) (assoc :community.address/zip-code
                               (:postal-code address))
        (zero? (rand-int 3)) (assoc :community.address/country "USA")))
    (fake/company-gen)))

(s/def ::community
  (s/with-gen
    (s/keys :req [:community/id
                  :community/name]
            :opt [:community.address/street
                  :community.address/zip-code
                  :community.address/city
                  :community.address/country])
    gen-community))

(def community-gen (s/gen ::community))


(def all-community-privs
  [:community.privs/member])
;; :community.privs/admin
;; :community.privs/owner])

(defn by-id
  [db id]
  (let [res (d/pull db '[*] [:community/id id])]
    (when (:community/id res)
      res)))

(def q-by-user-id
  '[:find (pull ?community-eid [*])
    :in $ ?user-id [?community-privs ...]
    :where
    [?user-eid :user/id ?user-id]
    [?user-eid ?community-privs ?community-eid]])

(defn by-user-id
  [db user-id]
  (->> (d/q q-by-user-id db user-id all-community-privs)
       flatten))

(def ^:private q-all
  '[:find (pull ?e pull-expr)
    :in $ pull-expr
    :where [?e :community/id]])

(defn all
  [db]
  (->> (d/q q-all db '[*])
       flatten))

(defn exists-with-name?
  [db name]
  (-> (d/pull db [:db/id] [:community/name name])
      :db/id
      some?))

(defn exists?
  [db id]
  (-> (d/pull db [:db/id] [:community/id id])
      :db/id
      some?))

(def ^:private q-find-with-name-like
  '[:find (pull ?e [*])
    :in $ ?lower-searched-word
    :where
    [?e :community/name ?comm-name]
    [(clojure.string/lower-case ?comm-name) ?lower-comm-name]
    [(clojure.string/includes? ?lower-comm-name ?lower-searched-word)]])

(defn find-with-name-like
  [db name]
  ;; Poor man's full text search
  (->> (d/q q-find-with-name-like
            db
            (str/lower-case name))
       flatten))

(defn- check-create
  [db community-input creator-id]
  (or (and (not (user/exists? db creator-id))
           (anomaly ::anomalies/incorrect "User doesn't exist"))
      (and (exists-with-name? db (:name community-input))
           (anomaly ::anomalies/conflict
                    "A community with this name already exists"))))

(defn- create-tx
  [{:keys [name street zip-code city country]}]
  (cond-> {:community/id   (UUID/randomUUID)
           :community/name name}
    street   (assoc :community.address/street street)
    zip-code (assoc :community.address/zip-code zip-code)
    city     (assoc :community.address/city city)
    country  (assoc :community.address/country country)))

;; TODO check input of this as being spec compliant
;; TODO extract full generation of tx
;; to prevent propagating the GraphQL camelCase naming
(defn create
  [conn creator-id input]
  (let [db (d/db conn)]
    (or (check-create db input creator-id)
        (let [community-eid   "tmp-community-eid"
              community       (assoc (create-tx input)
                                :db/id community-eid)
              community-privs (map (fn [priv]
                                     {:db/id [:user/id creator-id]
                                      priv   {:db/id community-eid}})
                                all-community-privs)
              res             (d/transact conn
                                          {:tx-data (conj community-privs
                                                          community)})]
          (->> (get-in res [:tempids community-eid])
               (d/pull (d/db conn) '[*]))))))

(def ^:private q-by-wod-id
  '[:find (pull ?community [*])
    :in $ ?wod-id
    :where
    [?wod :wod/id ?wod-id]
    [?wod :wod/community ?community]])

(defn by-wod-id
  [db id]
  (-> (d/q q-by-wod-id db id)
      ffirst))

(def ^:private q-member-count
  '[:find (count ?user)
    :in $ ?community-id
    :where
    [?community :community/id ?community-id]
    [?user :community.privs/member ?community]])

(defn member-count
  [db community-id]
  (-> (d/q q-member-count db community-id)
      ffirst
      (or 0)))

(def ^:private q-member?
  '[:find ?user
    :in $ ?community-id ?user-id
    :where
    [?user :user/id ?user-id]
    [?user :community.privs/member ?community]
    [?community :community/id ?community-id]])

(defn member?
  [db community-id user-id]
  (-> (d/q q-member? db community-id user-id)
      seq
      boolean))

(defn- user-join-check
  [db user-id community-id]
  (or (and (-> (d/pull db [:db/id] [:user/id user-id])
               :db/id
               nil?)
           (anomaly ::anomalies/incorrect "User doesn't exist"))
      (and (-> (d/pull db [:db/id] [:community/id community-id])
               :db/id
               nil?)
           (anomaly ::anomalies/incorrect "Community doesn't exist"))))

(defn user-join
  [conn {:keys [user-id community-id]}]
  (or (user-join-check (d/db conn) user-id community-id)
      (let [tx-data [{:db/id                  [:user/id user-id]
                      :community.privs/member {:db/id [:community/id
                                                       community-id]}}]]
        (d/transact conn {:tx-data tx-data}))))

;; TODO factorize with join-community-bad-input
(defn- check-user-leave
  [db user-id community-id]
  (or (and (-> (d/pull db [:db/id] [:user/id user-id])
               :db/id
               nil?)
           (anomaly ::anomalies/incorrect "User doesn't exist"))
      (and (-> (d/pull db [:db/id] [:community/id community-id])
               :db/id
               nil?)
           (anomaly ::anomalies/incorrect "Community doesn't exist"))))

(defn- user-leave-tx
  [user-id community-id]
  [:db/retract
   [:user/id user-id]
   :community.privs/member
   [:community/id community-id]])

(defn user-leave
  [conn input]
  (let [{:keys [community-id user-id]} input]
    (if-let [anom (check-user-leave (d/db conn) user-id community-id)]
      (resolve-as false (anomaly->error anom))
      (let [tx-data (user-leave-tx user-id community-id)]
        (d/transact conn {:tx-data [tx-data]})
        community-id))))
