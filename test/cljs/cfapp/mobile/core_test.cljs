(ns cfapp.mobile.core-test
  (:require
   [cljs.test :refer-macros [deftest testing is]]
   [cfapp.mobile.core :as core]))

(deftest fake-test
  (testing "fake description"
           (is (= 1 2))))
