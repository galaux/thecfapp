(ns cfapp.mobile.ui.component.wod-card.view
  (:require
   ["lucide-react" :refer [Check Timer]]
   [cfapp.mobile.controller.routing.core :as routing]
   [cfapp.mobile.controller.workout.util :as workout-util]
   [cfapp.mobile.ui.component.button.view :refer [button]]
   [cfapp.mobile.ui.component.card.style :as card-style]
   [cfapp.mobile.ui.component.chip.view :refer [chip]]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.vertical-stamp.view :refer [vertical-stamp]]
   [cfapp.mobile.ui.component.wod-card.style :as style]
   [clojure.string :as s]
   [cfapp.mobile.ui.component.icon.view :refer [icon]]
   [herb.core :refer [<class]]
   [cfapp.mobile.util :as util]))

(defn- ->application
  [{:keys [value unit]}]
  (let [{:keys [all men women]} value]
    (if (some? all)
      (workout-util/apply-ui-unit all unit)
      (str (workout-util/apply-ui-unit men unit)
           "/"
           (workout-util/apply-ui-unit women unit)))))

(defn- reps-and-applications
  [t]
  [text
   {:text-size :xsmall
    :color     :gray50}
   t])

(defn- exercise
  [{:keys [movement application rep-count]}]
  (let [rep-label         (when rep-count (str rep-count " reps"))
        application-label (when application (->application application))]
    [:div
     [:div (:name movement)]
     (cond
       (and (some? rep-label) (some? application-label))
       [reps-and-applications
        (str rep-label " · " application-label)]
       (some? rep-label)
       [reps-and-applications
        rep-label]
       (some? application-label)
       [reps-and-applications
        application-label])]))

(defn li
  [item]
  [:li
   {:class (<class (fn []
                     {:margin-left "15px"
                      :padding-top "10px"}))}
   item])

(defn workout-loop
  [{:keys [tasks] :as loop}]
  [:div
   {:class (<class (fn [] {:padding-top "10px"}))}
   (when-let [loop-type (workout-util/loop-title loop)]
     [:div (s/capitalize loop-type)])
   [:ul
    ;; Tasks don't have ids that we could use for keys so we use the index
    ;; which we should not do but workout tasks is not a dynamic list anyway
    (map-indexed (fn [i {:keys [movement] :as task}]
                   ^{:key i}
                   [li
                    (if (some? movement)
                      [exercise task]
                      [workout-loop task])])
                 tasks)]])

(defn- user-already-has-score
  [logged-user-id scores]
  (some (fn [{:keys [user]}]
          (= logged-user-id (:id user)))
        scores))

(defn wod-card
  [{:keys [href logged-user-id]} {:keys [id date workout scores]}]
  (let [href-create-score (:create-score href)
        wod-color-key     (case (workout-util/loop-type (:loop workout))
                            :amrap    :yellow
                            :emom     :purple
                            :for-time :green
                            :dark-blue)]
    [:div {:class (<class card-style/card wod-color-key)}
     [:div
      [:div {:class (<class style/header wod-color-key)}
       [text
        {:text-size      :small
         :text-transform "uppercase"}
        (:name workout)]
       (if (pos? (count scores))
         [:a
          {:style {:color :white}
           :href  (routing/href-wod-scores id)} ;; TODO
          [chip Timer (count scores) :color-key wod-color-key]]
         [chip Timer (count scores) :color-key wod-color-key])]
      [:div {:class (<class style/description)}
       [workout-loop (:loop workout)]]]
     [:div {:class (<class style/footer)}
      [apply vertical-stamp (util/month-and-day date)]
      (if (user-already-has-score logged-user-id scores)
        [icon Check]
        [button
         {:type    :link
          :variant wod-color-key
          :href    (href-create-score id)}
         "Add my score"])]]))
