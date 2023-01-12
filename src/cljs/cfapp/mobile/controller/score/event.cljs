(ns cfapp.mobile.controller.score.event
  (:require
   [re-frame.core :as rf]
   [pyramid.core :as p]))

(rf/reg-event-db
  ::on-create-score
  (fn [db [_ user-id wod value {:keys [data errors]}]]
    ;; TODO handle errors
    (if errors
      (println "Error while performing GQL call" errors)
      (let [score-id (get-in data [:createScore :id])
            user     (-> (p/pull db [{[:id user-id] [:id]}])
                         (get [:id user-id]))]
        (p/add db
               {:id    score-id
                :user  (update user :scores conj {:id score-id})
                :wod   (update wod :scores conj {:id score-id})
                :value value})))))
