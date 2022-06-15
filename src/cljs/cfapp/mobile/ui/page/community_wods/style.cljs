(ns cfapp.mobile.ui.page.community-wods.style)

(defn parent
  []
  {:position :static
   :margin   "0 auto"})

(defn parent-header
  []
  {:display         :flex
   :justify-content :space-between
   :padding         "0 20px"})

(defn child
  []
  {:position   :absolute
   :left       0
   :right      0
   :min-height "100px"
   :padding    "15px 0 0 0"})

(defn header
  []
  {:display         :flex
   :flex-direction  :column
   :justify-content :space-between})

(defn header-footer
  []
  {:display         :flex
   :justify-content :space-between
   :padding-top     "25px"})
