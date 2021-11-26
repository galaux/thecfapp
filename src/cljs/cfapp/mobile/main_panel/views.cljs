(ns cfapp.mobile.main-panel.views
  (:require
   ["/views" :as views]
   ["react" :as react]
   [cfapp.mobile.main-panel.subs :as subs]
   [cfapp.mobile.pages.find-community.views :refer [find-community]]
   [cfapp.mobile.pages.page-a.views :refer [page-a]]
   [cfapp.mobile.pages.page-b.views :refer [page-b]]
   [cfapp.mobile.routing.core :as routing]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(def suspense  (r/adapt-react-class react/Suspense))
(def layout (r/adapt-react-class views/Layout))

(defn main-panel
  []
  (let [page (condp = @(re-frame/subscribe [::subs/curr-page])
               :find-community find-community
               :page-a page-a
               :page-b page-b)]
    [suspense {:fallback "Loading …"}
     ;; TEMP to showcase navigation
     [:div
      [:a
       {:href (routing/path-for :page-a)}
       "Nav to A"]
      [:a
       {:href (routing/path-for :page-b)}
       "Nav to B"]
      [:a
       {:href (routing/path-for :find-community)}
       "Nav to FindCommunity"]
      [layout
       [page]]]]))
