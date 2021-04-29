(ns thecfapp.test-util-test
  (:require
    [clojure.test :refer [deftest is]]
    [thecfapp.test-util :as sut]))


(deftest test-osef
  (let [input "# Fragment of Score fields required to access @client field belongsToLoggedUser\nfragment ScoreWithBelongsToLoggedUserFields on Score {\n  belongsToLoggedUser @client\n  user {\n    id\n  }\n}\n"
        expected
        "# Fragment of Score fields required to  field belongsToLoggedUser\nfragment ScoreWithBelongsToLoggedUserFields on Score {\n  \n  user {\n    id\n  }\n}\n"]
    (is (= expected (sut/remove-client-fields input)))))
