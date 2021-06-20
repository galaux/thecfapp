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
   [ring.util.http-response :refer [ok]]
   [ring.util.response :refer [resource-response]]
   [ring-graphql-ui.core :refer [wrap-graphiql wrap-voyager]]))

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

(def react-app-handler
  "The React App build comprises a `index.html`, and some static assets. All
   must obviously be served but with a caveat. As the React App uses
   a browser-side router, it can generate routes only known by itself
   and not to the server. If user happens to hit Enter on the browser URL field,
   the current route will be requested to the server though it may be a browser
   route. For this reason, any route other than the ones corresponding to a
   known static asset, should return `index.html`in order to always fall-back
   to the mobile application."
  (ring/create-resource-handler
   {:path "/"
    ;; Serve all known static resources in `./resources/mobile`
    :root "mobile"
    ;; Don't redirect `/` to `/index.html`, we'll handle this with
    ;; `:not-found-handler` (prevents displaying `/index.html` on the broswer)
    :index-files []
    ;; Anything else than a know static file, we return the mobile app
    :not-found-handler (constantly
                        (resource-response "index.html"
                                           {:root "mobile"}))}))

(defn app-routes
  [execute-query]
  (-> (ring/ring-handler
       ;; Regular handler: API & al.
       (ring/router
        ["/api/graphql" {:post (partial serve-graphql execute-query)}])
       ;; Default handler: our React App
       react-app-handler)
      ;; Suplementary regular endpoints: GraphQL dev apps
      (wrap-graphiql {:endpoint "/api/graphql"})
      (wrap-voyager {:endpoint "/api/graphql"})))

;; TODO see how to properly handle error page (see cfapp-back)

(defmethod ig/init-key :cfapp.api/handler
  [_ {:keys [execute-query] :as all}]
  ;; FIXME use site-defaults
  ;; TODO use wrap-reload for dev
  (app-routes execute-query))
