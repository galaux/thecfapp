(ns cfapp.mobile.ui.page.create-workout.view
  (:require
   [cfapp.mobile.ui.component.button.view :refer [button]]
   [cfapp.mobile.ui.component.card.view :refer [card]]
   [cfapp.mobile.ui.component.layout.view :refer [layout]]
   [cfapp.mobile.ui.component.menu-wrapper.view :refer [menu-wrapper]]
   [cfapp.mobile.ui.component.text-area.view :refer [text-area]]
   [cfapp.mobile.ui.component.wod-card.view :refer [workout-card]]
   [reagent.core :as r]))

(defn- error
  [{:keys [message line-nb char-pos offender]}]
  (str "Line "
       line-nb
       ", character "
       char-pos
       ": "
       message
       (when offender
         (str " (offending part: \""
              offender
              "\")"))))

(defn create-workout
  [_props]
  (let
    [!state
     (r/atom
       "\"Osef\"\n\nFor Time\n100 Pull-Up\n100 Push-Up\n100 Sit-Up\n100 Air Squat")]
    (fn [{:keys [parse-result check-workout] :as props}]
      (let [{:keys [workout errors]} parse-result]
        [menu-wrapper
         (select-keys props
                      #{:logged-user
                        :menu-props})
         [layout
          ;; TODO Have the name of the Community instead
          {:sub-header "New Workout"
           :open-menu  (get-in props [:menu-props :open])}
          [:div
           [text-area {:value @!state :on-change #(reset! !state %)}]
           [button {:on-click #(check-workout @!state)} "Check"]
           (when (seq errors)
             (into [:ul]
                   (map #(vector :li (error %)) errors)))
           (when workout
             [workout-card workout])]]]))))
