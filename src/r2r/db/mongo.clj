(ns r2r.db.mongo
  (:require [monger.core :as monger]
            [monger.collection :as monger-coll])
  (:import [org.bson.types ObjectId]))


(defn connect-db []
  (monger/connect!)
  (monger/set-db! (monger/get-db "r2r")))


(defn insert [documents learning]
  (connect-db)
  (let [learning-id (ObjectId.)]
    (monger-coll/insert documents (assoc learning :id learning-id))))

(defn insert-revision-backlog [documents learning-id]
  (println "Inside insert-revision-backglo")
  (connect-db)
  (monger-coll/insert documents (assoc {:foo "bar"} :id (ObjectId.)))
  "fucked")

(defn get-learnings [documents]
  (connect-db)
  (monger-coll/find-maps documents))
