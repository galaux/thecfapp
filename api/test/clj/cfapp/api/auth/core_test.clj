(ns cfapp.api.auth.core-test
  (:require
   [cfapp.api.db.domain.user :as user]
   [cfapp.api.auth.password-encoder :as pw-encoder]
   [cfapp.api.spec-util :refer [strong-password]]
   [cfapp.api.test-util :as tutil]
   [cfapp.api.util :refer [encode-basic-auth]]
   [clojure.data.json :as json]
   [clojure.spec.gen.alpha :as gen]
   [clojure.test :refer [deftest is testing]]
   [datomic.client.api :as d]
   [integrant.core :as ig]
   [ring.mock.request :as mock])
  (:import java.net.URLEncoder))

(defn- request-with-creds
  [method uri]
  (-> (mock/request method uri)
      (mock/header :authorization
                   (encode-basic-auth "good" "creds"))))

(deftest static-auth

  (testing "No credentials"
    (let [{:keys [:cfapp.api/handler] :as system} (tutil/setup)
          request (mock/request :get "/api/user/i_do_not%40exist.com")]
      (let [{:keys [status body]} (handler request)]
        (is (= 401 status))
        (is (= "access denied" body)))
      (ig/halt! system)))

  (testing "Bad credentials"
    (let [{:keys [:cfapp.api/handler] :as system} (tutil/setup)
          request (-> (mock/request :get "/api/user/i_do_not%40exist.com")
                      (mock/header :authorization
                                   (encode-basic-auth "bad" "creds")))]
      (let [{:keys [status body]} (handler request)]
        (is (= 401 status))
        (is (= "access denied" body)))
      (ig/halt! system))))

(deftest get-user
  (let [{:user/keys [email] :as user-a} (gen/generate user/user-gen)]

    (testing "Unknown user"
      (let [{:keys [:cfapp.api/handler] :as system} (tutil/setup)
            request (request-with-creds :get "/api/user/i_do_not%40exist.com")]
        (let [{:keys [status body]} (handler request)]
         (is (= 204 status))
         (is (empty? body)))
        (ig/halt! system)))

    (testing "Known user"
      (let [{:keys [:cfapp.api.db/conn :cfapp.api/handler] :as system} (tutil/setup)
            request (request-with-creds :get (str "/api/user/" (URLEncoder/encode email)))]
        (tutil/domain-add conn [user-a])
        (let [{:keys [status body]} (handler request)]
          (is (= 200 status))
          (is (= {:user_id (str (:user/id user-a))
                  :email (:user/email user-a)}
                 (json/read-str body :key-fn keyword))))
        (ig/halt! system)))))

(deftest create-user
  (let [user-a (gen/generate user/user-gen)]

    (testing "Missing email"
      (let [{:keys [:cfapp.api/handler] :as system} (tutil/setup)
            request (-> (request-with-creds :post "/api/user")
                        (mock/json-body {:password strong-password}))]
        (let [{:keys [status body]} (handler request)]
          (is (= 422 status))
          (is (= "Invalid email" body)))
        (ig/halt! system)))

    (testing "Invalid email"
      (let [{:keys [:cfapp.api/handler] :as system} (tutil/setup)
            request (-> (request-with-creds :post "/api/user")
                        (mock/json-body {:email "not an email"
                                         :password strong-password}))]
        (let [{:keys [status body]} (handler request)]
          (is (= 422 status))
          (is (= "Invalid email" body)))
        (ig/halt! system)))

    (testing "Weak password"
      (let [{:keys [:cfapp.api/handler] :as system} (tutil/setup)
            request (-> (request-with-creds :post "/api/user")
                        (mock/json-body {:email "correct@email.com"
                                         :password "weak!"}))]
        (let [{:keys [status body]} (handler request)]
          (is (= 422 status))
          (is (= "Weak password" body)))
        (ig/halt! system)))

    (testing "Creation OK"
      (let [{:keys [:cfapp.api/handler :cfapp.api.db/conn] :as system} (tutil/setup)
            request (-> (request-with-creds :post "/api/user")
                        (mock/json-body {:email "correct@email.com"
                                         :password strong-password}))]
        (tutil/domain-add conn [user-a])
        (let [{:keys [status body]} (handler request)
              body (json/read-str body :key-fn keyword)
              {:keys [user/id]} (-> (d/db (:cfapp.api.db/conn system))
                                    (tutil/find-with-attr-and-value :user/email "correct@email.com")
                                    ffirst)]
          (is (= 200 status))
          (is (= {:user_id (str id)
                  :email "correct@email.com"}
                 body)))
        (ig/halt! system)))))

(deftest login-user
  (let [{:user/keys [id email] :as user-a} (gen/generate user/user-gen)]

    (testing "Unknown user"
      (let [{:keys [:cfapp.api/handler] :as system} (tutil/setup)
            request (-> (request-with-creds :post "/api/login")
                        (mock/json-body {:email "unknown@user.com"
                                         :password strong-password}))]
        (let [{:keys [status body]} (handler request)]
          (is (= 204 status))
          (is (empty? body)))
        (ig/halt! system)))

    (testing "Bad password"
      (let [{:keys [:cfapp.api/handler :cfapp.api.db/conn] :as system} (tutil/setup)
            request (-> (request-with-creds :post "/api/login")
                        (mock/json-body {:email email
                                         :password "bad pass"}))]
        (tutil/domain-add conn [user-a])
        (let [{:keys [status body]} (handler request)]
          (is (= 204 status))
          (is (empty? body)))
        (ig/halt! system)))

    (testing "Correct credentials"
      (let [{:keys [:cfapp.api/handler :cfapp.api.db/conn] :as system} (tutil/setup)
            request (-> (request-with-creds :post "/api/login")
                        (mock/json-body {:email email
                                         :password strong-password}))]
        (tutil/domain-add conn [user-a])
        (let [{:keys [status body]} (handler request)]
          (is (= 200 status))
          (is (= {:user_id (str id)
                  :email email}
                 (json/read-str body :key-fn keyword))))
        (ig/halt! system)))))

(deftest set-email-as-verified
  (let [user-a (gen/generate user/user-gen)]

    (testing "Invalid email"
      (let [{:keys [:cfapp.api/handler] :as system} (tutil/setup)
            uri (str "/api/user/not_an_email/email-verified")
            request (request-with-creds :post uri)]
        (let [{:keys [status body]} (handler request)]
          (is (= 422 status))
          (is (= "Invalid email" body)))
        (ig/halt! system)))

    (testing "Unknown user"
      (let [{:keys [:cfapp.api/handler] :as system} (tutil/setup)
            uri (str "/api/user/idont%40exist.com/email-verified")
            request (request-with-creds :post uri)]
        (let [{:keys [status body]} (handler request)]
          (is (= 422 status))
          (is (= "Unknown user" body)))
        (ig/halt! system)))

    (testing "Set email as verified ok"
      (let [{:keys [:cfapp.api/handler :cfapp.api.db/conn] :as system} (tutil/setup)
            uri (str "/api/user/" (:user/email user-a) "/email-verified")
            request (request-with-creds :post uri)]
        (tutil/domain-add conn [user-a])
        (let [{:keys [status body]} (handler request)]
          (is (= 204 status))
          (is (empty? body)))
        (ig/halt! system)))))

(deftest change-password
  (let [{:user/keys [email] :as user-a} (gen/generate user/user-gen)
        url-encoded-email (URLEncoder/encode email)
        new-password "aieiR@IOP&*()"]

    (testing "Missing password"
      (let [{:keys [:cfapp.api/handler
                    :cfapp.api.db/conn
                    :cfapp.api.auth/password-encoder]
             :as system} (tutil/setup)]
        (tutil/domain-add conn [user-a])
        (let [request (request-with-creds :put (str "/api/user/" url-encoded-email "/password"))
              {:keys [status body]} (handler request)
              {:keys [user/password]} (-> (d/db (:cfapp.api.db/conn system))
                                          (tutil/find-with-attr-and-value :user/email email)
                                          ffirst)]
          (is (= 422 status))
          (is (= "Missing password" body))
          (is (pw-encoder/matches? password-encoder strong-password password)))
        (ig/halt! system)))

    (testing "Weak password"
      (let [{:keys [:cfapp.api/handler
                    :cfapp.api.db/conn
                    :cfapp.api.auth/password-encoder]
             :as system} (tutil/setup)]
        (tutil/domain-add conn [user-a])
        (let [request (-> (request-with-creds :put (str "/api/user/" url-encoded-email "/password"))
                          (mock/json-body {:password "weak!"}))
              {:keys [status body]} (handler request)
              {:keys [user/password]} (-> (d/db (:cfapp.api.db/conn system))
                                          (tutil/find-with-attr-and-value :user/email email)
                                          ffirst)]
          (is (= 422 status))
          (is (= "Weak password" body))
          (is (pw-encoder/matches? password-encoder strong-password password)))
        (ig/halt! system)))

    (testing "Unknown user"
      (let [{:keys [:cfapp.api/handler] :as system} (tutil/setup)
            request (-> (request-with-creds :put (str "/api/user/" url-encoded-email "/password"))
                        (mock/json-body {:password strong-password}))]
        (let [{:keys [status body]} (handler request)]
          (is (= 422 status))
          (is (= "Unknown user" body)))
        (ig/halt! system)))

    (testing "Change password ok"
      (let [{:keys [:cfapp.api/handler
                    :cfapp.api.db/conn
                    :cfapp.api.auth/password-encoder]
             :as system} (tutil/setup)
            request (-> (request-with-creds :put (str "/api/user/" url-encoded-email "/password"))
                        (mock/json-body {:password new-password}))]
        (tutil/domain-add conn [user-a])
        (is (pw-encoder/matches? password-encoder strong-password (:user/password user-a)))
        (let [{:keys [status body]} (handler request)
              {:keys [user/password]} (-> (d/db (:cfapp.api.db/conn system))
                                          (tutil/find-with-attr-and-value :user/email email)
                                          ffirst)]

          (is (= 204 status))
          (is (empty? body))
          (is (pw-encoder/matches? password-encoder new-password password)))
        (ig/halt! system)))))
