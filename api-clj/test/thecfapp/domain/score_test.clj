(ns thecfapp.domain.score-test
  (:require
   [clojure.data.json :as json]
   [clojure.spec.gen.alpha :as gen]
   [clojure.test :refer [deftest is]]
   [integrant.core :as ig]
   [thecfapp.domain.user :as user]
   [thecfapp.domain.wod :as wod]
   [thecfapp.domain.workout :as workout]
   [thecfapp.test-util :as tutil])
  (:import
   java.util.UUID))

(deftest create-score
  (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)
        fragments [:Score]
        user-a (gen/generate user/user-gen)
        workout-a (gen/generate workout/workout-gen)
        wod-a (assoc (gen/generate wod/wod-gen)
                     :wod/workout workout-a)
        variables {:userId (.toString (:user/id user-a))
                   :wodId (.toString (:wod/id wod-a))
                   :value (inc (rand-int 5000))}
        gql-query (tutil/test-query :CreateScore fragments variables)]
    (tutil/domain-add conn [user-a wod-a])
    (let [response (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          actual-score (-> parsed-response :data :createScore)
          expected-score {:value (:value variables)}]
      (is (empty? (:errors parsed-response)))
      (is (= 200 (:status response)))
      (is (= expected-score (dissoc actual-score :id)))
      (is (uuid? (UUID/fromString (:id actual-score)))))
    (ig/halt! system)))
