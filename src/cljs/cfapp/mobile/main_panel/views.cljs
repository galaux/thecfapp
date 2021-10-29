(ns cfapp.mobile.main-panel.views
  (:require
   ["/views" :as views]
   ["react" :as react]
   [cfapp.mobile.main-panel.subs :as subs]
   [cfapp.mobile.pages.find-community.views :refer [find-community]]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(def suspense  (r/adapt-react-class react/Suspense))
(def layout (r/adapt-react-class views/Layout))

(defn main-panel
  []
  (let [page (condp = @(re-frame/subscribe [::subs/curr-page])
               :find-community find-community)]
    [suspense {:fallback "Loading …"}
     [layout
      [page]]]))
