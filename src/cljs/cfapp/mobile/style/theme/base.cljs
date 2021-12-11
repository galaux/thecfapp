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
   :large  "large"})

(def colors
  ;; Names in comments correspond to names given in our palette:
  ;; https://xd.adobe.com/view/033e9162-93f3-4a87-89b4-653e104188e3-566d/screen/92aecc9d-e9ad-4d4b-b630-f55c6fb62678/specs/
  {:background "#1D1D22"
   ;; Brand colors
   :brand1     "#8ABF4C" ;; Green
   :brand2     "#046A38" ;; Dark green
   :brand3     "#06555A" ;; Deep green
   ;; Gray scales
   :black      "#000000"
   :gray80     "#32332F" ;; Black
   :gray60     "#565952" ;; Gray
   :gray50     "#888C81" ;; Light grey
   :gray40     "#C6CCBC" ;; Lighter grey
   :gray30     "#D9D9DA" ;; Off gray
   :gray20     "#F2F2EE" ;; Off white
   :gray10     "#F8F8F8" ;; Off gray #2
   :white      "#FFFFFF" ;; Pure white
   ;; Complementary colors
   :info       "#31B8C7" ;; Blue
   :success    "#8ABF4C" ;; Success
   :warning    "#F5BD3E" ;; Orange
   :danger     "#E86546"});; Red

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
