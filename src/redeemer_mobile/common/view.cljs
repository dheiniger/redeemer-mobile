(ns redeemer-mobile.common.view
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [clojure.string :as str]))

(def ReactNative (js/require "react-native"))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def logo-img (js/require "./images/logo.png"))
(def menu-img (js/require "./images/hamburger-icon.png"))
(def menu-width 310)


(defn header [state]
  [view {:style {:height 60}}
   [menu state]
   [image {:source logo-img
           :style  {:width 40 :height 60 :margin-top -5 :margin-right 5 :position "absolute" :resizeMode "contain" :flex 1 :right "0%" :top "0%"}}]])

(defn menu [state]
  (if (= :open state)
    [menu-open]
    [menu-closed]))

(defn menu-closed []
  [view
   [touchable-highlight {:on-press       #(re-frame.core/dispatch [:menu-opened])
                         :style          {:height 50 :width 50}
                         :underlay-color "white"}
    [image {:source menu-img
            :style  {:height 25 :width 35 :margin 8 :position "absolute" :resizeMode "contain" :flex 1 :left "0%" :top 0}}]]])

(defn menu-open []
  [view {:style {:width menu-width :height 900 :background-color "white"}}
   [touchable-highlight {:on-press       #(re-frame.core/dispatch [:menu-closed])
                         :style          {:height 50 :width 50 :left (- menu-width 50)}
                         :underlay-color "white"}
    [image {:source menu-img
            :style  {:height 25 :width 35 :position "absolute" :resizeMode "contain" :flex 1 :left "0%" :top 8}}]]
   [menu-items]])

(defn menu-items []
  [view
   [menu-item logo-img "Home"]
   [menu-item logo-img "Learn" "https://redeemernorwalk.org/learn/"]
   [menu-item logo-img "Sermons" "https://redeemernorwalk.org/sermons/"]
   [menu-item logo-img "Blog" "https://redeemernorwalk.org/blog/"]
   [menu-item logo-img "Counseling"]
   [menu-item logo-img "Contact"]])

(defn menu-item [icon menu-text request-url]
  [view {:style {:height 45}}
   [touchable-highlight {:on-press       #(re-frame.core/dispatch [:option-pressed (make-keyword menu-text) request-url])
                         :style          {:width menu-width :height 45}
                         :underlay-color "white"}
    [view
     [image {:source icon
             :style  {:height 45 :width 40}}]
     [text {:style {:font-size 30 :flex 1 :position "absolute" :left 60}}
      menu-text]]]])

(defn make-keyword [menu-text]
  (keyword (str/replace menu-text " " "-" )))

