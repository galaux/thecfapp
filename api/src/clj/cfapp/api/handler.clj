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
   [ring.util.http-response :refer [ok]]
   [clojure.tools.logging :as log]))

;; TODO see old lein/luninus project on how to setup a default handler
;; (def default-handler
;;   (ring/create-default-handler
;;    {:not-found
;;     (constantly {:status 404
;;                  :body "404 - Page not found"})
;;     :method-not-allowed
;;     (constantly {:status 405
;;                  :body "405 - Not allowed"})
;;     :not-acceptable
;;     (constantly {:status 406
;;                  :body "406 - Not acceptable"})}))

(defn app-routes
  [execute-query]
  (ring/ring-handler
    (ring/router
     ["/api"
      ["/graphql" {:post (fn [req]
                           (-> (:body req)
                               slurp
                               (json/read-str :key-fn keyword)
                               execute-query
                               ;; FIXME shouldn't this be done by a middleware?
                               json/write-str
                               ok))}]
      ["/math" {:get {:parameters {:query {:x int?, :y int?}}
                      :responses  {200 {:body {:total int?}}}
                      :handler    (fn [req]
                                    (let [{{:keys [x y]} :query-params} req]
                                      {:status 200
                                       :body   {:total (+ x y)}}))}}]
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
                           multipart/multipart-middleware]}}])))
              ;; :middleware [parameters/parameters-middleware
              ;;              rrc/coerce-request-middleware
              ;;              rrc/coerce-response-middleware]}})))
              ;;              ;; muuntaja/format-response-middleware]}})))

(defmethod ig/init-key :cfapp.api/handler
  [_ {:keys [execute-query] :as all}]
  ;; FIXME use site-defaults
;; (see ~/TheCFApp/sources/cfapp-back/src/clj/galaux/cfapp_back/middleware.clj:54)
  (app-routes execute-query))
  ;; (-> app-routes
  ;;     (wrap-defaults api-defaults)))
      ;; wrap-reload))
