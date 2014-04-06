(ns r2r.controller
  (:require [r2r.db.mongo :as r2r-db]))


(defn- add-revision-backlog [learning-id]
  (r2r-db/insert-revision-backlog "revision" learning-id))

(defn add-learning [learning]
  (add-revision-backlog (r2r-db/insert "learnings" learning)))


(defn get-learnings []
  (r2r-db/get-learnings "learnings"))