(ns cfapp.mobile.core
  (:require
   [cfapp.mobile.auth.core :as auth]
   [cfapp.mobile.config :as config]
   [cfapp.mobile.main-panel.events :as events]
   [cfapp.mobile.main-panel.views :refer [main-panel]]
   [cfapp.mobile.routing.core :as routing]
   cfapp.mobile.theme
   re-promise.core
   [re-frame.core :as re-frame]
   [re-graph.core :as re-graph]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [cfapp.mobile.auth.db]))

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
  (re-frame/dispatch-sync [::events/initialize-db])
  (auth/init! config/auth)
  (dev-setup)
  (mount-root))
