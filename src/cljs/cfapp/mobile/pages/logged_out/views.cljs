(ns cfapp.mobile.pages.logged-out.views
  (:require
   [cfapp.mobile.auth.events :as auth]
   [re-frame.core :as rf]))

(defn logged-out
  []
  [:div
   [:h1 "You are logged out"]
   [:a
    {:href     "#"
     :on-click #(rf/dispatch [::auth/login])}
    "Click here to log back in"]])
