(ns r2r.controller
  (:require [r2r.db.mongo :as r2r-db]))


(defn- add-revision-backlog [learning-id]
  (r2r-db/insert-revision-backlog "revision" learning-id))

(defn add-learning [learning user]
  (println "Adding a new learning for " user)
  (add-revision-backlog (r2r-db/insert "learnings" learning user)))


(defn  get-learnings [user]
  (println "The current logged in user is " user)
  (r2r-db/get-learnings "learnings"))