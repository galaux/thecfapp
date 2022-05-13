(ns cfapp.mobile.core
  (:require
   [cfapp.mobile.controller.auth.core :as auth]
   [cfapp.mobile.config :as config]
   [cfapp.mobile.ui.main-panel.event :as event]
   [cfapp.mobile.ui.main-panel.view :refer [main-panel]]
   [cfapp.mobile.controller.routing.core :as routing]
   cfapp.mobile.ui.style.global
   re-promise.core
   [re-frame.core :as re-frame]
   [re-graph.core :as re-graph]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [cfapp.mobile.controller.auth.db]))

(defn dev-setup
  []
  (when config/debug?
    (println "dev mode")))

;; https://lilac.town/writing/modern-react-in-cljs-error-boundaries/
;; https://github.com/reagent-project/reagent/blob/master/doc/ReactFeatures.md#error-boundaries
(defn err-boundary
  [& children]
  (let [err-state (r/atom nil)]
    (r/create-class
      {:display-name        "ErrBoundary"
       :component-did-catch (fn [err info]
                              (reset! err-state [err info]))
       :reagent-render      (fn [& children]
                              (if (nil? @err-state)
                                (into [:<>] children)
                                (let [[_ info] @err-state]
                                  [:pre [:code (pr-str info)]])))})))

(defn ^:dev/after-load mount-root
  []
  (js/console.log "Start …")
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch [::re-graph/init config/gql])
  (routing/start-browser-history-handler!)
  (let [root-el (.getElementById js/document "root")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [err-boundary [main-panel]] root-el)))

(defn ^:dev/before-load stop
  []
  (routing/stop-browser-history-handler!)
  (re-frame/dispatch [::re-graph/destroy])
  (js/console.log "… stop."))

(defn init
  []
  (re-frame/dispatch-sync [::event/initialize-db])
  (auth/init! config/auth)
  (dev-setup)
  (mount-root))
