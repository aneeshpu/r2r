(ns r2r.db.mongo
  (:require [monger.core :as monger]
            [monger.collection :as monger-coll])
  (:import [org.bson.types ObjectId]))


(defn connect-db []
  (monger/connect!)
  (monger/set-db! (monger/get-db "r2r")))


(defn insert [documents learning]
  (connect-db)
  (monger-coll/insert documents (assoc learning :id (ObjectId.))))