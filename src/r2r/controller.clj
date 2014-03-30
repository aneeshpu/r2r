(ns r2r.controller
  (:require [r2r.db.mongo :as r2r-db]))

(defn add-learning [learning]

  (println learning "---->question" (:question learning) "answer:" (:answer learning))
  (r2r-db/insert "learnings" learning))