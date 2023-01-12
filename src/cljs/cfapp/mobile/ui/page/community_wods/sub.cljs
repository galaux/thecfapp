(ns cfapp.mobile.ui.page.community-wods.sub
  (:require
   [cfapp.common.graphql :refer [gql->clj]]
   [pyramid.core :as p]
   [re-frame.core :as rf]))

(def ^:private pp-exercise
  [{:movement
    [:id
     :name]}
   {:application
    [{:value [:all
              :men
              :women]}
     :unit]}
   :rep-count])

(def ^:private pp-score
  [:id
   {:user [:id]}
   {:wod [:id]}
   :value])

(def ^:private community-pull
  [:id
   :name
   :member-count
   {:address
    [:street
     :zip-code
     :city
     :country]}
   {:wods
    [:id
     :date
     {:workout
      [:id
       :name
       {:loop [:time-cap
               :duration
               :period
               :round-count
               :score-type
               {:tasks [;; Exercise
                        {:movement
                         [:id
                          :name]}
                        {:application
                         [{:value
                           [:all
                            :men
                            :women]}
                          :unit]}
                        :rep-count
                        ;; Workout loop
                        :time-cap
                        :duration
                        :period
                        :round-count
                        :score-type
                        {:tasks pp-exercise}]}]}]}
     {:scores pp-score}]}])

(rf/reg-sub
  ::community
  (fn [db [_ id]]
    (let [community-p-id [:id id]
          db-data        (p/pull db
                                 [{[:id id]
                                   community-pull}])]
      (gql->clj (get db-data community-p-id)))))
