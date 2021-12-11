(ns cfapp.mobile.ui.molecule.menu.core
  (:require
   [reagent.core :as r]))

(defn init-state
  []
  (let [atom-open? (r/atom true)
        open       #(swap! atom-open? (constantly true))
        close      #(swap! atom-open? (constantly false))]
    {:atom-open? atom-open?
     :open       open
     :close      close}))
