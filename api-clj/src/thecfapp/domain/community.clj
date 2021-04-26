(ns thecfapp.domain.community
  (:require
    [clojure.spec.alpha :as s]
    [clojure.spec.gen.alpha :as gen]
    [clojure.string :as str]
    [cognitect.anomalies :as anomalies]
    [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
    [datomic.client.api :as d]
    [thecfapp.domain.user :as user]
    [thecfapp.util :refer [anomaly anomaly? anomaly->error]]
    [thecfapp.spec-util :as sutil])
  (:import
    (java.util UUID)))

(s/def :community/id :util/uuid)
(s/def :community/name
  (s/with-gen
    :util/non-blank-string
    (constantly (gen/fmap #(str "CrossFit " %)
                          sutil/non-blank-string-gen))))
(s/def :community.address/street string?)
(s/def :community.address/zip-code string?)
(s/def :community.address/city string?)
(s/def :community.address/country string?)

(s/def ::community
  (s/keys :req [:community/id
                :community/name]
    :opt [:community.address/street
          :community.address/zip-code
          :community.address/city
          :community.address/country]))

(def community-gen (s/gen ::community))


(def all-community-privs
  [:community.privs/member])
;; :community.privs/admin
;; :community.privs/owner])

(defn shape-community-address
  [community]
  (let [{:community.address/keys [street zip-code city country]} community]
    {:street street
     :zipCode zip-code
     :city city
     :country country}))

(defn shape-community
  [community]
  (let [{:community/keys [id name]} community]
    {:id id
     :name name
     :address (shape-community-address community)}))

(def ^:private find-all-query
  '[:find (pull ?e pull-expr)
    :in $ pull-expr
    :where [?e :community/id]])

(defn get-community
  [{:keys [conn]} {:keys [id]} _value]
  (let [res (d/pull (d/db conn) '[*] [:community/id id])]
    (when (:community/id res)
      (shape-community res))))

(def q-user-communities
  '[:find (pull ?community-eid [*])
    :in $ ?user-id [?community-privs ...]
    :where
    [?user-eid :user/id ?user-id]
    [?user-eid ?community-privs ?community-eid]])

(defn user-communities*
  [db user-id]
  (->> (d/q q-user-communities db user-id all-community-privs)
       flatten))

(defn user->communities
  [{:keys [conn]} _args {:keys [id]}]
  (if id
    (->> (user-communities* (d/db conn) id)
         (map shape-community))
    []))

(defn user-communities
  [{:keys [conn]} {:keys [userId]} _value]
  (->> (user-communities* (d/db conn) userId)
       (map shape-community)))

(defn list-communities
  [{:keys [conn]} _args _value]
  (->> (d/q find-all-query (d/db conn) '[*])
       flatten
       (map shape-community)))

(defn exists-with-name?
  [conn name]
  (-> (d/pull (d/db conn) [:db/id] [:community/name name])
      :db/id
      some?))

(defn find-with-name-like
  [{:keys [conn]} {:keys [name]} _value]
  ;; Poor man's full text search
  (->> (d/q '[:find (pull ?e [*])
              :in $ ?lower-searched-word
              :where
              [?e :community/name ?comm-name]
              [(clojure.string/lower-case ?comm-name) ?lower-comm-name]
              [(clojure.string/includes? ?lower-comm-name ?lower-searched-word)]]
            (d/db conn)
            (str/lower-case name))
       flatten
       (map shape-community)))

(defn check-add-community
  [conn community-input creator-id]
  (or (and (not (user/exists? conn creator-id))
           (anomaly ::anomalies/incorrect "User doesn't exist"))
      (and (exists-with-name? conn (:name community-input))
           (anomaly ::anomalies/conflict
                    "A community with this name already exists"))))

(defn add-community-tx
  [input]
  (let [{:keys [name]} input]
    {:community/id (UUID/randomUUID)
     :community/name name}))

;; TODO check input of this as being spec compliant
;; TODO extract full generation of tx
;; to prevent propagating the GraphQL camelCase naming
(defn add-community*
  [{:keys [conn]} {:keys [creatorId input]} _value]
  (or (check-add-community conn input creatorId)
      (let [community-eid "tmp-community-eid"
            community (assoc (add-community-tx input)
                        :db/id community-eid)
            community-privs (map (fn [priv]
                                   {:db/id [:user/id creatorId]
                                    priv {:db/id community-eid}})
                              all-community-privs)
            res (d/transact conn {:tx-data (conj community-privs community)})]
        (->> (get-in res [:tempids community-eid])
             (d/pull (d/db conn) '[*])))))

(defn add-community
  [context args value]
  (let [res (add-community* context args value)]
    (if (anomaly? res)
      (resolve-as nil (anomaly->error res))
      (shape-community res))))

(def q-by-wod-id
  '[:find (pull ?community [*])
    :in $ ?wod-id
    :where
    [?wod :wod/id ?wod-id]
    [?wod :wod/community ?community]])

(defn by-wod-id
  [{:keys [conn]} _args {:keys [id]}]
  (-> (d/q q-by-wod-id (d/db conn) id)
      ffirst
      shape-community))

(def q-member-count
  '[:find (count ?user)
    :in $ ?community-id
    :where
    [?community :community/id ?community-id]
    [?user :community.privs/member ?community]])

(defn member-count
  [{:keys [conn]} _args {:keys [id]}]
  (-> (d/q q-member-count (d/db conn) id)
    ffirst
    (or 0)))

(defn user-join-bad-input
  [db user-id community-id]
  (or (and (-> (d/pull db [:db/id] [:user/id user-id])
               :db/id
               nil?)
           (anomaly ::anomalies/incorrect "User doesn't exist"))
      (and (-> (d/pull db [:db/id] [:community/id community-id])
               :db/id
               nil?)
           (anomaly ::anomalies/incorrect "Community doesn't exist"))))

(defn- user-join*
  [conn {:keys [userId communityId]}]
  (or (user-join-bad-input (d/db conn) userId communityId)
      (let [tx-data [{:db/id [:user/id userId]
                      :community.privs/member {:db/id [:community/id
                                                       communityId]}}]]
        (d/transact conn {:tx-data tx-data}))))

(defn user-join
  [context args _value]
  (let [{:keys [db-after] :as result} (user-join* (:conn context) (:input args))]
    (if (anomaly? result)
      (resolve-as false (anomaly->error result))
      (-> (d/pull db-after '[*] [:community/id (-> args :input :communityId)])
          shape-community))))

;; TODO factorize with join-community-bad-input
(defn check-leave-community
  [db user-id community-id]
  (or (and (-> (d/pull db [:db/id] [:user/id user-id])
               :db/id
               nil?)
           (anomaly ::anomalies/incorrect "User doesn't exist"))
      (and (-> (d/pull db [:db/id] [:community/id community-id])
               :db/id
               nil?)
           (anomaly ::anomalies/incorrect "Community doesn't exist"))))

(defn leave-community-tx
  [user-id community-id]
  [:db/retract
   [:user/id user-id]
   :community.privs/member
   [:community/id community-id]])

(defn leave-community
   "Returns the communityId (FIXME that doesn't really make sense.
   Return sthing more meaningful like the user or the community
   depending on what mobile could need)"
  [context {:keys [input]} _value]
  (let [{:keys [communityId userId]} input]
    (if-let [anom (check-leave-community (d/db (:conn context)) userId communityId)]
      (resolve-as false (anomaly->error anom))
      (let [tx-data (leave-community-tx userId communityId)]
        (d/transact (:conn context) {:tx-data [tx-data]})
        communityId))))
