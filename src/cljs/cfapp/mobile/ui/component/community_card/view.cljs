(ns cfapp.mobile.ui.component.community-card.view
  (:require
   ["lucide-react" :refer [Users]]
   [cfapp.mobile.ui.component.icon.view :refer [icon]]
   [cfapp.mobile.ui.component.button.view :refer [button]]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.community-card.style :as style]
   [herb.core :refer [<class]]))

(defn member-count-chip
  [member-count]
  [:div
   {:class (<class style/member-count-chip)}
   [:div
    {:class (<class style/member-count-chip-icon)}
    [icon {:size 12} Users]]
   [text
    {:text-size :xxsmall}
    member-count]])

(defn community-card
  [{:keys [community join-community leave-community]}]
  (let [{:keys [id name address is-member? member-count]} community]
    [:div
     {:class (<class style/community-card)}
     [:div
      {:class (<class style/top-line)}
      [text
       {:text-size      :small
        :text-transform "uppercase"}
       name]
      [member-count-chip member-count]]
     [:div
      {:class (<class style/address)}
      [:p [text {:text-size :xxsmall} (:street address)]]
      [:p [text {:text-size :xxsmall} (:zip-code address)]]
      [:p [text {:text-size :xxsmall} (:city address)]]
      [:p [text {:text-size :xxsmall} (:country address)]]]
     [:div
      {:class (<class style/bottom-line)}
      (if is-member?
        [button
         {:variant  :danger
          :on-click #(leave-community id)}
         "Leave"]
        [button
         {:variant  :primary
          :on-click #(join-community id)}
         "Join"])]]))
