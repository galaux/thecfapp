(ns cfapp.mobile.core
  (:require
   [cfapp.mobile.config :as config]
   [cfapp.mobile.main-panel.events :as events]
   [cfapp.mobile.main-panel.views :refer [main-panel]]
   [re-frame.core :as re-frame]
   [re-graph.core :as re-graph]
   [reagent.core :as r]
   [reagent.dom :as rdom]))

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
     {:display-name "ErrBoundary"
      :component-did-catch (fn [err info]
                             (reset! err-state [err info]))
      :reagent-render (fn [& children]
                        (if (nil? @err-state)
                          (into [:<>] children)
                          (let [[_ info] @err-state]
                            [:pre [:code (pr-str info)]])))})))

(defn ^:dev/after-load mount-root
  []
  (js/console.log "Start …")
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "root")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [err-boundary [main-panel]] root-el)))

(defn ^:dev/before-load stop []
  (re-frame/dispatch [::re-graph/destroy])
  (js/console.log "… stop."))

(def ^:private gql-config
  {:ws   nil
   :http {:url    "http://localhost:4000/api/graphql"
          ;; implementation-specific options (see clj-http or hato for options,
          ;; defaults to {}, may be a literal or a function that returns the options
          :impl   {}
          :supported-operations #{:query :mutate}}})

(defn init
  []
  (re-frame/dispatch-sync [::events/initialize-db])
  (re-frame/dispatch      [::re-graph/init gql-config])
  (dev-setup)
  (mount-root))
