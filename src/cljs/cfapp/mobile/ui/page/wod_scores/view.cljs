(ns cfapp.mobile.ui.page.wod-scores.view
  (:require
   [cfapp.mobile.ui.page.abstract-wod-score.view :refer [abstract-wod-score]]
   [cfapp.mobile.ui.page.wod-scores.style :as style]
   [herb.core :refer [<class]]
   [reagent.core :as r]
   [cfapp.mobile.controller.workout.util :as workout-util]))

(defn- minutes-and-seconds
  [value]
  (let [value-second (/ value 1000)
        minutes      (int (/ value-second 60))
        seconds      (mod value-second 60)]
    [minutes seconds]))

(defn- rounds-and-reps
  [reps-per-round value]
  (let [rounds (int (/ value reps-per-round))
        reps   (mod value reps-per-round)]
    [rounds reps]))

(defn wod-scores
  [{:keys [create-score] :as props} {:keys [workout scores] :as wod}]
  (let [state          (r/atom {})
        score-type     (get-in workout [:loop :score-type])
        scores         (reverse (sort-by :value scores))
        reps-per-round (workout-util/reps-per-round (:workout wod))]
    (fn []
      [abstract-wod-score
       props
       wod
       [:div
        {:key   :header-label
         :class (<class style/header-label)}
        "Score Board"]
       [:div
        {:key   :score-card
         :class (<class style/score-card)}
        [:div
         ;; TODO add feature to sort list by score or username
         ;; TODO by default we should sort it by something else than score
         ;; so that user doesn't feel like we are shaming her/him for potential
         ;; repetitive low scores
         (for [{:keys [id user value]} scores]
           [:ul
            {:key   id
             :class (<class style/user-name)}
            [:li
             (:name user)
             [:span {:class (<class style/value)}
              (case score-type
                "DURATION"
                (let [[minutes seconds] (minutes-and-seconds value)]
                  (str minutes "' " seconds "\""))
                "REP_COUNT"
                (let [[rounds reps] (rounds-and-reps reps-per-round value)]
                  (str rounds " rounds, " reps " reps")))]]])]]])))
