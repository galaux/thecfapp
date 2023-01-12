(ns cfapp.mobile.ui.page.create-score.sub
  (:require
   [pyramid.core :as p]
   [re-frame.core :as rf]))

(def ^:private pp-exercise  ;; TODO factorize with community-wod/sub
  [{:movement
    [:id
     :name]}
   {:application
    [{:value [:all
              :men
              :women]}
     :unit]}
   :rep-count])

(def ^:private wod-pull ;; TODO factorize with community-wod/sub
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
                      {:tasks pp-exercise}]}]}]}])

(rf/reg-sub
  ::wod
  (fn [db [_ wod-id]]
    (-> (p/pull db [{[:id wod-id] wod-pull}])
        (get [:id wod-id]))))
