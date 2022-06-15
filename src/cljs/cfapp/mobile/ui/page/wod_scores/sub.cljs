(ns cfapp.mobile.ui.page.wod-scores.sub
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

(def ^:private pp-loop
  [:time-cap
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
            {:tasks pp-exercise}]}])

(def ^:private pp-score
  [:id
   {:user [:id :name]}
   :value])

(def ^:private wod-pull ;; TODO factorize with community-wod/sub
  [:id
   :date
   {:workout
    [:id
     :name
     {:loop pp-loop}]}
   {:scores pp-score}])

(rf/reg-sub
  ::wod-with-scores
  (fn [db [_ wod-id]]
    (-> (p/pull db [{[:id wod-id] wod-pull}])
        (get [:id wod-id]))))
