(ns cfapp.mobile.ui.page.community-wods.style)

(defn parent
  []
  {:position :static
   :margin   "0 auto"})

(defn child
  []
  {:position   :absolute
   :left       0
   :right      0
   :min-height "100px"
   :padding    "15px 0 0 0"})
