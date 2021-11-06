(ns cfapp.api.handler
  (:require
   [cfapp.api.auth.core :as auth]
   [cfapp.common.config :refer [server-config server-config-placeholder]]
   [clojure.data.json :as json]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [integrant.core :as ig]
   [reitit.ring :as ring]
   [ring-graphql-ui.core :refer [wrap-graphiql wrap-voyager]]
   [ring.middleware.basic-authentication
    :refer
    [wrap-basic-authentication]]
   [ring.middleware.cors :as cors]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.middleware.jwt :refer [wrap-jwt]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.util.http-response :refer [ok unauthorized]]
   [ring.util.response :refer [response]]))

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

(defn inject-runtime-config
  "Inject mobile app configuration into the served `index.html`. When run
   locally, the mobile app run by Shadow CLJS uses a default configuration but
   when running live, we need to pass this configuration that depends on the
   environment."
  [server-url jwt-conf body]
  (let [server-config-str (json/write-str (server-config {:server-url server-url
                                                          :jwt jwt-conf}))]
    (str/replace-first body
                       (str "\"" server-config-placeholder "\"")
                       server-config-str)))

(defn mobile-index
  [server-url jwt-conf]
  (->> (io/resource "public/index.html")
       slurp
       (inject-runtime-config server-url jwt-conf)
       response))

(defn react-app-handler
  "The React App build comprises a `index.html`, and some static assets. All
   must obviously be served but with a caveat. As the React App uses
   a browser-side router, it can generate routes only known by itself
   and not to the server. If user happens to hit Enter on the browser URL field,
   the current route will be requested to the server though it may be a browser
   route. For this reason, any route other than the ones corresponding to a
   known static asset, should return `index.html`in order to always fall-back
   to the mobile application."
  [server-url jwt-conf]
  (ring/create-resource-handler
    {:path "/"
     ;; Serve all known static resources in `./resources/public`
     :root "public"
     ;; Disable serving `/index.html` when asked for `/`, we'll handle this with
     ;; `:not-found-handler` (prevents displaying `/index.html` on the browser
     ;; address bar.
     ;; FIXME This strategy of serving `index.html` through the defaut handler
     ;; hides missing assets: if Mobile requests an asset that we don't serve
     ;; but that we SHOULD serve, we'll return the content of index.html with
     ;; 200 OK!
     :index-files []
     ;; Anything else than a know static file, we return the mobile app
     :not-found-handler (constantly (mobile-index server-url jwt-conf))}))

(defn- check-static-auth-fn
  [{:keys [static-auth]}]
  (let [{:keys [id password]} static-auth]
    (fn [in-id in-password]
      (and (= in-id id)
           (= in-password password)))))

(defn- check-jwt-client-id-fn
  [jwt-conf]
  (if (:reject-missing-token? jwt-conf)
    (fn [claims] (= (:aud claims) (:client-id jwt-conf)))
    (constantly true)))

;; FIXME only use this for local dev
(defn- wrap-cors
  [handler]
  (cors/wrap-cors
   handler
   ;; TODO config?
   :access-control-allow-origin      [#"http://localhost:8280"]
   :access-control-allow-methods     [:get :put :post :delete]
   :access-control-allow-credentials "true"))

(defn app-routes
  [execute-query serve-mobile {:keys [server-url jwt-conf] :as context}]
  (let [check-rest-auth     (check-static-auth-fn context)
        check-jwt-client-id (check-jwt-client-id-fn jwt-conf)]
    (-> (ring/ring-handler
          ;; Regular handler: API & al.
          (ring/router
            [["/api/graphql" ;; TODO factorize with cfapp.mobile.config
              {:post       (fn [{:keys [body claims]}]
                             ;; Don't accept JWT from other environments
                             (if (check-jwt-client-id claims)
                               (ok (execute-query body))
                               (unauthorized)))
               :middleware [#(wrap-jwt % jwt-conf)
                            ;; FIXME use Muuntaja for these 2:
                            #(wrap-json-body % {:keywords? true})
                            wrap-json-response
                            wrap-cors]}]
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
               ;; deleting users rather than setting a status to "disabled"
               ;; which we don't support yet. Currently, the only way a user
               ;; deletion could be triggered is through Auth0 UI. Calling code
               ;; snippet on Auth0 side should throw an exception right away.
               ["/:email/password"
                {:put (partial auth/change-user-password context)}]
               ["/:email/email-verified"
                {:post (partial auth/set-email-as-verified context)}]]]])
          ;; Default handler: our React App
          (when serve-mobile
            (react-app-handler server-url jwt-conf)))
        ;; supplementary regular endpoints: GraphQL dev apps
        (wrap-graphiql {:endpoint "/api/graphql"})
        (wrap-voyager {:endpoint "/api/graphql"}))))

;; TODO see how to properly handle error page (see cfapp-back)

(defmethod ig/init-key :cfapp.api/handler
  [_ {:keys [execute-query serve-mobile] :as deps}]
  ;; FIXME use [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
  ;; TODO use wrap-reload for dev
  (let [context (select-keys deps
                             #{:conn
                               :jwt-conf
                               :execute-query
                               :password-encoder
                               :password-strength-checker
                               :server-url
                               :static-auth})]
    (app-routes execute-query serve-mobile context)))
