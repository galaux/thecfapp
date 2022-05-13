(ns cfapp.mobile.ui.component.menu-wrapper.core
  (:require
   [cfapp.mobile.controller.auth.event :as auth-evt]
   [cfapp.mobile.controller.routing.core :as routing]
   [re-frame.core :as rf]
   [reagent.core :as r]))

(defn init-props
  ([]
   (init-props false))
  ([open?]
   (let [atom-open? (r/atom open?)
         open       #(reset! atom-open? true)
         close      #(reset! atom-open? false)]
     {:atom-open? atom-open?
      :open       open
      :close      close
      :log-out    #(rf/dispatch [::auth-evt/logout])
      :nav-to-find-community #(do
                                (close)
                                (routing/nav-to :find-community))
      :nav-to-my-communities #(do
                                (close)
                                (routing/nav-to :my-communities))})))
