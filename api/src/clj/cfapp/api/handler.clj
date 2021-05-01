(ns cfapp.api.handler
  (:require
   [clojure.data.json :as json]
   [integrant.core :as ig]
   [muuntaja.core :as m]
   reitit.coercion.spec
   [reitit.ring :as ring]
   [reitit.ring.coercion :as coercion]
   [reitit.ring.middleware.multipart :as multipart]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
   [ring.util.http-response :refer [ok]]))

(def api-router-data
  ;; router data affecting all routes
  {:data {:coercion   reitit.coercion.spec/coercion
          :muuntaja   m/instance
          :middleware [;; query-params & form-params
                       parameters/parameters-middleware
                       ;; content-negotiation
                       muuntaja/format-negotiate-middleware
                       ;; encoding response body
                       muuntaja/format-response-middleware
                       ;; exception handling
                       coercion/coerce-exceptions-middleware
                       ;; decoding request body
                       muuntaja/format-request-middleware
                       ;; coercing response bodys
                       coercion/coerce-response-middleware
                       ;; coercing request parameters
                       coercion/coerce-request-middleware
                       ;; multipart
                       multipart/multipart-middleware]}})

(defn serve-graphql
  [execute-query req]
  (-> (:body req)
      slurp
      (json/read-str :key-fn keyword)
      execute-query
      ;; FIXME shouldn't this be done by a middleware?
      json/write-str
      ok))

(defn app-routes
  [execute-query]
  ;; https://github.com/metosin/reitit/blob/master/doc/ring/static.md#external-routes
  (ring/ring-handler
   (ring/router
    ["/api/graphql" {:post (partial serve-graphql execute-query)}])
   (-> (ring/create-file-handler {:path "/"
                                  :root "../packages/mobile/build"})
       (wrap-defaults site-defaults))))
       ;; (ring/create-default-handler))))

;; TODO see how to properly handle error page (see cfapp-back)

(defmethod ig/init-key :cfapp.api/handler
  [_ {:keys [execute-query] :as all}]
  ;; FIXME use site-defaults
  ;; TODO use wrap-reload for dev
  (app-routes execute-query))
