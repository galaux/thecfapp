(ns cfapp.mobile.ui.page.create-score.view
  (:require
   [cfapp.mobile.ui.component.button.view :refer [button]]
   [cfapp.mobile.ui.component.input-group.view :refer [input-group]]
   [cfapp.mobile.ui.page.abstract-wod-score.view :refer [abstract-wod-score]]
   [cfapp.mobile.ui.page.create-score.style :as style]
   [herb.core :refer [<class]]
   [reagent.core :as r]))

(defn create-score
  [{:keys [create-score] :as props} {:keys [workout] :as wod}]
  (let [state      (r/atom {})
        score-type (get-in workout [:loop :score-type])]
    (fn []
      [abstract-wod-score
       props
       wod
       [:div
        {:key   :header-label
         :class (<class style/header-label)}
        "Add my score"]
       [:div
        {:key   :score-card
         :class (<class style/score-card)}
        [:div ;; To push the footer
         (if (= score-type "DURATION") ;; TODO change for keyword
           [:div {:class (<class style/card-body)}
            [:div {:class (<class style/input-group)}
             [input-group
              {:id        :minutes
               :type      :number
               :label     "Minutes"
               :value     (:minutes @state)
               :on-change #(swap! state assoc :minutes %)}]]
            [:div
             {:class (<class style/input-group)}
             [input-group
              {:id        :seconds
               :type      :number
               :label     "Seconds"
               :value     (:seconds @state)
               :on-change #(swap! state assoc :seconds %)}]]]
           [:div {:class (<class style/card-body)}
            [:div {:class (<class style/input-group)}
             [input-group
              {:id        :rounds
               :type      :number
               :label     "Rounds"
               :value     (:rounds @state)
               :on-change #(swap! state assoc :rounds %)}]]
            [:div {:class (<class style/input-group)}
             [input-group
              {:id        :reps
               :type      :number
               :label     "Reps"
               :value     (:reps @state)
               :on-change #(swap! state assoc :reps %)}]]])]
        [:div {:class (<class style/footer)}
         [button
          {:on-click #(create-score @state)}
          "Add my score"]]]])))
