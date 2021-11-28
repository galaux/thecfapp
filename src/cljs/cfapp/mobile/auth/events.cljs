(ns cfapp.mobile.auth.events
  (:require
   [re-frame.core :as rf]
   [re-frame.db :as rf-db]))

(rf/reg-event-fx
  ::login
  (fn [{:keys [db]} _]
    (let [client  (get-in db [:auth :client])
          options #js
                   {:redirect_uri js/window.location.origin
                    ;; The piece of data (the key of a page) that will be passed
                    ;; on to us when user gets redirected back to the app
                    ;; TODO read this default from config
                    :appState     "find-community"}]
      {:dispatch [::set-loading]
       :promise  {:call       #(.loginWithRedirect client options)
                  ;; No `:on-success` because we are currently sending user out
                  ;; of the app. She will be redirected back to us later.
                  :on-failure [::set-error]}})))

(rf/reg-fx
  ::logout
  (fn []
    (let [client  (get-in @rf-db/app-db [:auth :client])
          options #js {:returnTo (str js/window.location.origin "/logged-out")}]
      (try
        (.logout client options)
        (rf/dispatch [::set-logged-out])
        (catch js/Error error
          (rf/dispatch [::set-error error]))))))

(rf/reg-event-fx
  ::logout
  (fn [_ _]
    {::logout nil}))

(rf/reg-event-db
  ::set-loading
  (fn [db [_ client]]
    (update db
            :auth
            merge
            (merge {:stage :loading}
                   (when client
                     {:client client})))))

(rf/reg-event-db
  ::set-logged-in
  (fn [db [_ user-data]]
    (update db
            :auth
            merge
            {:stage     :logged-in
             :user-data user-data})))

(rf/reg-event-db
  ::set-logged-out
  (fn [db _]
    (update db
            :auth
            merge
            {:stage     :logged-out
             :user-data nil})))

(rf/reg-event-db
  ::set-error
  (fn [db [_ error]]
    (update db
            :auth
            merge
            {:stage :error
             :error error})))
