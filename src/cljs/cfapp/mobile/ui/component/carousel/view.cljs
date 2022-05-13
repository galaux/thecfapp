(ns cfapp.mobile.ui.component.carousel.view
  (:require
   ["react-swipeable-views" :default SwipeableViews]))

(defn carousel
  [& children]
  [:> SwipeableViews
   {:style      {:padding "0 30px"}
    :slideStyle {:padding "0 15px"}
    :enableMouseEvents true}
   children])
