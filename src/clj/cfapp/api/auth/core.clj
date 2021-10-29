(ns cfapp.api.auth.core
  (:require
   [cfapp.api.db.domain.user :as user]
   [cfapp.api.auth.password-encoder :refer [matches?]]
   [cfapp.api.util :refer [anomaly? some-entity? valid-email?]]
   [cognitect.anomalies :as anomalies]
   [datomic.client.api :as d]
   [ring.util.http-response :refer
    [no-content ok unprocessable-entity]]))

(defn- shape-user
  [{:user/keys [id email]}]
  {:user_id (str id)
   :email   email})

;; TODO Keep track of user created for test from the Auth0 UI?
;; When testing the creation of a user from Auth0 UI, we get a `:debug true`
;; {:tenant "cfapp"
;;  :connection "Username-Password-Authentication"
;;  :email "g@alaux.net"
;;  :password "hahafdsfdfdEEEEWWW##$!@#"
;;  :debug true}
(defn create-user
  [{:keys [conn password-encoder password-strength-checker]} {:keys [body]}]
  (let [res (user/create conn password-encoder password-strength-checker body)]
    (if (anomaly? res)
      (unprocessable-entity (::anomalies/message res))
      (ok (shape-user res)))))

(defn user-by-email
  [{:keys [conn]} {:keys [path-params]}]
  (let [res (user/by-email (d/db conn) (:email path-params))]
    (if (some-entity? res)
      (ok (shape-user res))
      (no-content))))

(defn login-user
  [{:keys [conn password-encoder]} {:keys [body]}]
  (let [{:keys [email password]} body
        user                     (when (and (seq password)
                                            (valid-email? email))
                                   (user/by-email (d/db conn) email))]
    (if (and (some-entity? user)
             (matches? password-encoder password (:user/password user)))
      (ok (shape-user user))
      ;; Don't return "401 unauthorized" because that currently means
      ;; "Wrong static auth creds"
      (no-content))))

(defn set-email-as-verified
  [{:keys [conn]} {:keys [path-params]}]
  (let [res (user/set-email-as-verified conn (:email path-params))]
    (if (anomaly? res)
      (unprocessable-entity (::anomalies/message res))
      (no-content))))

(defn change-user-password
  [{:keys [conn password-encoder password-strength-checker]}
   {:keys [path-params body]}]
  (let [res (user/change-password conn
                                  password-encoder
                                  password-strength-checker
                                  (:email path-params)
                                  (:password body))]
    (if (anomaly? res)
      (unprocessable-entity (::anomalies/message res))
      (no-content))))
