(ns cfapp.api.handler-test
  (:require
   [cfapp.api.handler :as sut]
   [clojure.test :refer [deftest is testing]]
   [ring.mock.request :as mock]
   [buddy.core.keys :as keys]
   [clojure.java.io :as io]
   [clojure.string :as s]))

(deftest handler
  (let [{:keys [edkey valid-token]} (-> (io/resource "handler-data.edn")
                                        slurp
                                        read-string)
        valid-req-no-token          (mock/request :post "/api/graphql")
        public-key                  (keys/jwk->public-key edkey)
        jwt-conf                    {:issuers
                                       {"https://cfapp.eu.auth0.com/"
                                          {:alg        :RS256
                                           :public-key public-key}}
                                     :reject-missing-token? true}
        context                     {:conn                      nil
                                     :jwt-conf                  jwt-conf
                                     :password-encoder          nil
                                     :password-strength-checker nil}
        execute-query               (constantly "Success")
        serve-mobile                false
        handle                      (sut/app-routes execute-query
                                                    serve-mobile
                                                    context)]

    (testing "returns 401 when no JWT is provided"
             (let [actual (handle valid-req-no-token)]
               (is (= {:status 401 :body "No token found."}
                      actual))))

    (testing
      "returns 401 when invalid JWT is provided"
      (let [bad-token (s/replace-first valid-token #"g" "h")
            actual    (handle (assoc-in valid-req-no-token
                                [:headers "authorization"]
                                (str "Bearer " bad-token)))]
        (is
          (=
            {:status 401
             :body
               "The Token's Signature resulted invalid when verified using the Algorithm: SHA256withRSA"}
            actual))))

    (testing "returns 401 when JWT is too old"
             (let [actual                (handle (assoc-in valid-req-no-token
                                                   [:headers "authorization"]
                                                   (str "Bearer " valid-token)))
                   {:keys [status body]} actual]
               (is (= status 401))
               (is (s/starts-with? body "The Token has expired on "))))))

;; TODO write tests for cases
;; - "returns correct result when JWT is correct"
;;   Needs to be able to generate a token: just ship a static encryption key
;;   pair
;; - "returns 401 when JWT audience is wrong"
;;   Use encryption key pair from above
