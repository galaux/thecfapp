(ns cfapp.mobile.config
  (:require [cfapp.common.config :refer [server-config-placeholder server-config]]))

(def debug?
  ^boolean goog.DEBUG)

(defn local?
  []
  (= (.. js/window -SERVER_CONFIG)
     server-config-placeholder))

(def ^:private config
  (if (local?)
    ;; If running, locally, get a default "server config"
    (server-config {})
    (-> (.. js/window -SERVER_CONFIG)
        (js->clj :keywordize-keys true)
        server-config)))

(def server-url (get-in config [:server-url]))

(def gql
  {:ws   nil
   :http {:url    (str server-url "/api/graphql")
          ;; implementation-specific options (see clj-http or hato for options,
          ;; defaults to {}, may be a literal or a function that returns the options
          :impl   {}
          :supported-operations #{:query :mutate}}})

