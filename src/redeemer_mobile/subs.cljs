(ns redeemer-mobile.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :get-page
  (fn [db _]
    (:page db)))

(reg-sub
  :get-menu-state
  (fn [db _]
    (:menu-state db)))

(reg-sub
  :get-learn-page-content
  (fn [db _]
    (:learn-page-content db)))
