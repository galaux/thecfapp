(ns cfapp.mobile.ui.component.layout.view
  (:require
   ["lucide-react" :refer [Menu User]]
   [cfapp.mobile.ui.component.icon.view :refer [icon]]
   [cfapp.mobile.ui.component.layout.style :as style]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.style.theme.core :as theme]
   [clojure.string :as s]
   [herb.core :refer [<class]]))

(defn layout
  [{sub-header :header :keys [open-menu]} body]
  [:div {:class (<class style/container)}
   [:div {:class (<class style/header-wrapper)}
    [:div {:class (<class style/header)}
     [:div {:class (<class style/top-line)}
      [:div {:on-click open-menu}
       [icon Menu]]
      [text
       ;; TODO change letter spacing
       {:font-weight    :bold
        :text-transform :uppercase
        :text-size      :medium}
       theme/app-name]
      [icon User]]
     [:div {:class (<class style/sub-header)}
      (if (string? sub-header)
        [text
         {:color          :white
          :font-weight    :bold
          :text-transform :uppercase}
         sub-header]
        sub-header)]]
    [:div {:class (<class style/body)}
     body]]
   [:div {:class (<class style/footer)}
    [text
     {:text-size :xxxsmall}
     (s/join " " ["© 2022" theme/app-name ", all rights reserved"])]]])
