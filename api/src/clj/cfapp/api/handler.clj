(ns cfapp.api.handler
  (:require
   [cfapp.api.auth.core :as auth]
   [integrant.core :as ig]
   [reitit.ring :as ring]
   [ring-graphql-ui.core :refer [wrap-graphiql wrap-voyager]]
   [ring.middleware.basic-authentication
    :refer
    [wrap-basic-authentication]]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.util.http-response :refer [ok]]
   [ring.util.response :refer [resource-response]]))

;; (def api-router-data
;;   ;; router data affecting all routes
;;   {:data {:coercion   reitit.coercion.spec/coercion
;;           :muuntaja   m/instance
;;           :middleware [;; query-params & form-params
;;                        parameters/parameters-middleware
;;                        ;; content-negotiation
;;                        muuntaja/format-negotiate-middleware
;;                        ;; encoding response body
;;                        muuntaja/format-response-middleware
;;                        ;; exception handling
;;                        coercion/coerce-exceptions-middleware
;;                        ;; decoding request body
;;                        muuntaja/format-request-middleware
;;                        ;; coercing response bodys
;;                        coercion/coerce-response-middleware
;;                        ;; coercing request parameters
;;                        coercion/coerce-request-middleware
;;                        ;; multipart
;;                        multipart/multipart-middleware]}})

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

(defn- check-static-auth-fn
  [{:keys [static-auth]}]
  (let [{:keys [id password]} static-auth]
    (fn [in-id in-password]
      (and (= in-id id)
           (= in-password password)))))

(defn app-routes
  [execute-query context]
  (let [check-rest-auth (check-static-auth-fn context)]
    (-> (ring/ring-handler
         ;; Regular handler: API & al.
         (ring/router
          [["/api/graphql"
            {:post (fn [{:keys [body]}] (ok (execute-query body)))
             :middleware [#(wrap-json-body % {:keywords? true})
                          wrap-json-response]}]
           ;; Potential security improvement: filter the auth endpoints by the
           ;; Auth0 server IPs (privided in the Auth0 UI)
           ;; TODO check inputs
           ["/api"
            {:middleware [#(wrap-basic-authentication % check-rest-auth)
                          wrap-params
                          #(wrap-json-body % {:keywords? true})
                          wrap-json-response]}
            ["/login"
             {:post (partial auth/login-user context)}]
            ["/user"
             ["/:email"
              {:get (partial auth/user-by-email context)}]
             [""
              {:post (partial auth/create-user context)}]
             ;; "delete-user" not implemented on purpose: we should not be
             ;; deleting users rather than setting a status to "disabled" which
             ;; we don't support yet. Currently, the only way a user deletion
             ;; could be triggered is through Auth0 UI. Calling code snippet on
             ;; Auth0 side should throw an exception right away.
             ["/:email/password"
              {:put (partial auth/change-user-password context)}]
             ["/:email/email-verified"
              {:post (partial auth/set-email-as-verified context)}]]]])
         ;; Default handler: our React App
         react-app-handler)
        ;; Supplementary regular endpoints: GraphQL dev apps
        (wrap-graphiql {:endpoint "/api/graphql"})
        (wrap-voyager {:endpoint "/api/graphql"}))))

;; TODO see how to properly handle error page (see cfapp-back)

(defmethod ig/init-key :cfapp.api/handler
  [_ {:keys [execute-query] :as deps}]
  ;; FIXME use [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
  ;; TODO use wrap-reload for dev
  (let [context (select-keys deps
                             #{:conn
                               :execute-query
                               :password-encoder
                               :password-strength-checker
                               :static-auth})]
    (app-routes execute-query context)))
