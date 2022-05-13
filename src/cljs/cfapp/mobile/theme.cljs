(ns cfapp.mobile.theme
  (:require
   [garden.stylesheet :refer [at-font-face]]
   [cfapp.mobile.style.theme.core :refer [color]]
   [herb.core :refer [defglobal]]))

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

(defglobal
  fonts
  [(at-font-face
     {:font-family "Aaux Next"
      :font-weight 400
      :font-style "normal"
      :src ["url(./assets/fonts/aauxnext-rg-webfont.woff2) format('woff2')"
            "url(./assets/fonts/aauxnext-rg-webfont.woff)  format('woff')"]})
   (at-font-face
     {:font-family "Aaux Next"
      :font-weight 500
      :font-style "normal"
      :src ["url(./assets/fonts/aauxnext-md-webfont.woff2) format('woff2')"
            "url(./assets/fonts/aauxnext-md-webfont.woff)  format('woff')"]})
   (at-font-face
     {:font-family "Aaux Next"
      :font-weight 600
      :font-style "normal"
      :src ["url(./assets/fonts/aauxnext-sm-webfont.woff2) format('woff2')"
            "url(./assets/fonts/aauxnext-sm-webfont.woff)  format('woff')"]})
   (at-font-face
     {:font-family "Aaux Next"
      :font-weight 700
      :font-style "normal"
      :src ["url(./assets/fonts/aauxnext-bd-webfont.woff2) format('woff2')"
            "url(./assets/fonts/aauxnext-bd-webfont.woff)  format('woff')"]})])

(defglobal
  global
  "Our base global theme ported from Views.
   Changes to Views's `GlobalStyle` must be manually reflected here."
  [:html :body :#root {:height "100%"}]
  [:html
   {:background (color :background) ;; FIXME use `color`
    :color      (color :white)}]
  [:root [:>div {:height "100%"}]]
  [:body
   {:font-family "'Aaux Next', sans-serif"
    :-webkit-font-smoothing "antialiased"
    :-moz-osx-font-smoothing "grayscale"}]
  [:* {:box-sizing "border-box"}
   [:before {:box-sizing "border-box"}]
   [:after {:box-sizing "border-box"}]])
