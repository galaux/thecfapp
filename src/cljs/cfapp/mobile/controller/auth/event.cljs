(ns cfapp.mobile.controller.auth.event
  (:require
   [pyramid.core :as p]
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
  ::set-authenticated
  (fn [db [_ user-data]]
    (assoc-in db [:auth :user-data] user-data)))

(rf/reg-event-db
  ::set-logged-in
  ;; TODO handle error
  (fn [db [_ {:keys [data errors]}]]
    (-> db
        (assoc-in [:auth :stage] :logged-in)
        (p/add {:logged-user (:user data)}))))

(rf/reg-event-db
  ::set-logged-out
  (fn [db _]
    (-> db
        (dissoc :logged-user)
        (update :auth dissoc :user-data)
        (assoc-in [:auth :stage] :logged-out))))

(rf/reg-event-db
  ::set-error
  (fn [db [_ error]]
    (update db
            :auth
            merge
            {:stage :error
             :error error})))


;; (rf/reg-event-db
;;  ::on-got-user
;;  (fn [db [_ {:keys [data errors]}]]
;;    ;; TODO handle errors
;;    (let [user (get data :user)]
;;      (assoc db :logged-user user))))
