(ns cfapp.mobile.ui.page.logged-out.view
  (:require
   [cfapp.mobile.controller.auth.event :as auth]
   [re-frame.core :as rf]))

(defn logged-out
  []
  [:div
   [:h1 "You are logged out"]
   [:a
    {:href     "#"
     :on-click #(rf/dispatch [::auth/login])}
    "Click here to log back in"]])
