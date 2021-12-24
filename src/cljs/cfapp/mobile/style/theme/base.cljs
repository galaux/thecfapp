(ns cfapp.mobile.style.theme.base)

(def border-radiuses
  {:xxxsmall ".25rem"
   :xxsmall  ".5rem"
   :xsmall   ".625rem"
   :small    ".75rem"
   :medium   "1rem"
   :large    "2rem"
   :xlarge   "3rem"
   :round    "50%"})

(def border-widths
  {:medium "1px"
   :large  "2px"})

(def colors
  {:background "#1D1D22"
   :blue       "#1160D6"
   :dark-blue  "#204582"
   ;; Gray scales
   :black      "#000000"
   :gray90     "#1A151A"
   :gray80     "#2F2A2F"
   :gray60     "#484848"
   :gray50     "#808080"
   :gray30     "#C1BFC1"
   :gray10     "#DADADB"
   :white      "#FFFFFF"
   ;; Complementary colors
   :info       "#31B8C7"
   :success    "#8ABF4C"
   :warning    "#F5BD3E"
   :danger     "#9b2c2c"})

(def edge-sizes
  {:xxxsmall "0.25rem"
   :xxsmall  "0.5rem"
   :xsmall   "0.75rem"
   :small    "1rem"
   :medium   "1.25rem"
   :large    "1.5rem"
   :xlarge   "1.875rem"
   :xxlarge  "2.5rem"
   :xxxlarge "3.125rem"})

(def font-weights
  {:regular   400
   :medium    500
   :semi-bold 600
   :bold      700})

;; FIXME use this or maybe Garden has a better way?
(def text-sizes
  {:xxxsmall {:size   ".625rem"
              :height "1.4"}
   :xxsmall  {:size   ".75rem"
              :height "1.4"}
   :xsmall   {:size   ".875rem"
              :height "1.5"}
   :small    {:size   "1rem"
              :height "1.5"}
   :medium   {:size   "1.25rem"
              :height "1.5"}
   :large    {:size   "1.75rem"
              :height "1.6"}})
