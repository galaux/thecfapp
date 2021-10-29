(ns cfapp.mobile.theme
  (:require
   ["/views" :as views]
   [herb.core :refer [defglobal]]))

(def theme
  (js->clj views/theme :keywordize-keys true))

(defglobal
  reset-css
  "http://meyerweb.com/eric/tools/css/reset/
   v2.0 | 20110126
   License: none (public domain)"
  [:html :body :div :span :applet :object :iframe
   :h1 :h2 :h3 :h4 :h5 :h6 :p :blockquote :pre
   :a :abbr :acronym :address :big :cite :code
   :del :dfn :em :img :ins :kbd :q :s :samp
   :small :strike :strong :sub :sup :tt :var
   :b :u :i :center
   :dl :dt :dd :ol :ul :li
   :fieldset :form :label :legend
   :table :caption :tbody :tfoot :thead :tr :th :td
   :article :aside :canvas :details :embed
   :figure :figcaption :footer :header :hgroup
   :menu :nav :output :ruby :section :summary
   :time :mark :audio :video
   {:margin         0
    :padding        0
    :border         0
    :font-size      "100%"
    :font           ""
    :vertical-align "baseline"}]
  ;; HTML5 display-role reset for older browsers
  [:article :aside :details :figcaption :figure
   :footer :header :hgroup :menu :nav :section {:display "block"}]
  [:body {:line-height 1}]
  [:ol :ul {:list-style "none"}]
  [:blockquote :q {:quotes "none"}]
  [:blockquote [:before :after {:content "none"}]]
  [:q [:before :after {:content "none"}]]
  [:table {:border-collapse "collapse" :border-spacing 0}])

(defglobal global
           "Our base global theme ported from Views.
            Changes to Views's `GlobalStyle` must be manually reflected here."
           [:html :body :#root {:height "100%"}]
           [:html {:background (get-in theme [:colors :background])}]
           [:root [:>div {:height "100%"}]]
           [:body
            {:font-family "'Aaux Next', sans-serif"
             :-webkit-font-smoothing "antialiased"
             :-moz-osx-font-smoothing "grayscale"}]
           [:* {:box-sizing "border-box"}
            [:before {:box-sizing "border-box"}]
            [:after {:box-sizing "border-box"}]])
