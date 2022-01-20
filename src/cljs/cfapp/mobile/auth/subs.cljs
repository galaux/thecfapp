(ns cfapp.mobile.auth.subs
  (:require
   [re-frame.core :as rf]
   [pyramid.core :as p]))

(rf/reg-sub
  ::stage
  (fn [db]
    (get-in db [:auth :stage])))

(def ^:private user-pull
  [:id
   :name
   :email])

(rf/reg-sub
  ::logged-user
  (fn [db]
    (-> (p/pull db [{:logged-user user-pull}])
        :logged-user)))
