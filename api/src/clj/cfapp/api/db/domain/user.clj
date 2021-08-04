(ns cfapp.api.db.domain.user
  (:require
   [cfapp.api.auth.password-encoder :refer [encode]]
   [cfapp.api.auth.password-strength-checker :refer [strong?]]
   [cfapp.api.spec-util :refer [argon2id-encoded-str]]
   [cfapp.api.util :refer [anomaly valid-email? some-entity?]]
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [cognitect.anomalies :as anomalies]
   [datomic.client.api :as d]
   [talltale.core :as fake])
  (:import java.util.UUID))

(s/def :user/id :util/uuid)
(s/def :user/password :util/argon2id)
(s/def :user/screen-name :util/non-blank-string)
(s/def :user/email :util/email)
(s/def :user/email-verified? boolean?)

(defn- gen-user
  []
  (gen/fmap
    (fn [{:keys [email username]}]
      {:user/id (UUID/randomUUID)
       :user/password argon2id-encoded-str
       :user/screen-name (str username (rand-int 100))
       :user/email email
       :user/email-verified? (= (rand-int 2) 0)})
    (fake/person-gen)))

(s/def ::user
  (s/with-gen
    (s/keys :req [:user/id
                  :user/password
                  :user/screen-name
                  :user/email
                  :user/email-verified?])
    gen-user))

(def user-gen (s/gen ::user))

(def anomaly-invalid-email
  (anomaly ::anomalies/incorrect "Invalid email"))
(def anomaly-unknown-user
  (anomaly ::anomalies/incorrect "Unknown user"))
(def anomaly-weak-password
  (anomaly ::anomalies/incorrect "Weak password"))

(defn by-id
  [db id]
  (d/pull db '[*] [:user/id id]))

(defn by-email
  [db email]
  (d/pull db '[*] [:user/email email]))

(defn with-email
  [db email]
  (-> (d/q '[:find ?user
             :in $ ?email
             :where
             [?user :user/email ?email]
             [?user :user/id]]
        db email)
      ffirst))

(defn- create-tx
  [password-encoder {:keys [email password]}]
  {:user/id (UUID/randomUUID)
   ;; See following note about screen name and email at creation
   :user/screen-name email
   :user/email email
   :user/email-verified? false
   :user/password (encode password-encoder password)})

;; Note: Auth0 doesn't allow us to enforce a username on their side (with free
;; account) so at creation time, they only pass us an email and a password. We
;; are thus forced to set user's screen name to her email which is obviously not
;; ideal. User will be able to set it to something else later in her profile but
;; she should then not be able to set it to any other email address!
(defn- check-create
  [db pw-strength-checker {:keys [email password]}]
  (or (and ;; Auth0 enforces password strength, this function only persists the
           ;; user but let's also check Auth0 rules are met just for security.
           (not (strong? pw-strength-checker password))
           anomaly-weak-password)
      (and (not (valid-email? email))
           anomaly-invalid-email)
      (and (some? (with-email db email))
           (anomaly ::anomalies/incorrect "Email unavailable"))))

(defn create
  [conn password-encoder password-strength-checker input]
  (or (check-create (d/db conn) password-strength-checker input)
      (let [tx-data (create-tx password-encoder input)
            {:keys [db-after]} (d/transact conn {:tx-data [tx-data]})]
        (by-id db-after (:user/id tx-data)))))

(defn exists?
  [db id]
  (-> (d/pull db [:db/id] [:user/id id])
      :db/id
      some?))

(def ^:private q-all
  '[:find (pull ?e pull-expr)
    :in $ pull-expr
    :where [?e :user/id]])

(defn all
  [db]
  (->> (d/q q-all db '[*])
       flatten))

(def q-by-score-id
  '[:find (pull ?user [*])
    :in $ ?score-id
    :where
    [?score :score/user ?user]
    [?score :score/id ?score-id]])

(defn by-score-id
  [db score-id]
  (->> (d/q q-by-score-id db score-id)
       ffirst))

(defn check-set-email-as-verified
  [db email]
  (or (and (not (valid-email? email))
           anomaly-invalid-email)
      (and (not (some-entity? (by-email db email)))
           anomaly-unknown-user)))

(defn- set-email-as-verified-tx
  [email]
  {:user/email email
   :user/email-verified? true})

(defn set-email-as-verified
  [conn email]
  (or (check-set-email-as-verified (d/db conn) email)
      (let [tx-data (set-email-as-verified-tx email)
            {:keys [db-after]} (d/transact conn {:tx-data [tx-data]})]
        db-after)))

(defn check-change-password
  [db pw-strength-checker email password]
  (or (and (empty? password)
           (anomaly ::anomalies/incorrect "Missing password"))
      (and (not (strong? pw-strength-checker password))
           anomaly-weak-password)
      (and (not (some-entity? (by-email db email)))
           anomaly-unknown-user)))

(defn change-password-tx
  [password-encoder email password]
  {:user/email email
   :user/password (encode password-encoder password)})

(defn change-password
  [conn password-encoder password-strength-checker email password]
  (or (check-change-password (d/db conn) password-strength-checker email password)
      (let [tx-data (change-password-tx password-encoder email password)
            {:keys [db-after]} (d/transact conn {:tx-data [tx-data]})]
        db-after)))
