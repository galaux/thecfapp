(ns cfapp.common.config)

(def server-config-placeholder "__SERVER_CONFIG__")

(defn server-config
  "Generate the configuration the API passes to the Mobile by injecting it into
   the `index.html`. This function is invoked by the API but also by the Mobile,
   when run locally, to get a default configuration so it MUST return a valid
   configuration when passed an empty map."
  [{:keys [server-url jwt]}]
  (let [{:keys [domain client-id]} jwt]
    {:server-url (or server-url "http://localhost:4000")
     :jwt        {:domain    domain
                  :client-id client-id}}))
