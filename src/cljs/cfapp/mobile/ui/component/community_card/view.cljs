(ns cfapp.mobile.ui.component.community-card.view
  (:require
   ["lucide-react" :refer [Users]]
   [cfapp.mobile.ui.component.chip.view :refer [chip]]
   [cfapp.mobile.ui.component.button.view :refer [button]]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.community-card.style :as style]
   [herb.core :refer [<class]]))

(defn community-card
  [{:keys [community join-community leave-community]}]
  (let [{:keys [id href name address is-member? member-count]} community]
    [:div
     {:class (<class style/community-card)}
     [:div
      {:class (<class style/top-line)}
      [:a ;; TODO factorize as a component
       {:class (<class style/link)
        :href  href}
       [text
        {:text-size      :small
         :text-transform "uppercase"}
        name]]
      [chip Users member-count]]
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
