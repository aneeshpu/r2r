(ns r2r.core-test
  (:require [clojure.test :refer :all]
            [r2r.controller :refer :all]
            [r2r.db.mongo :as r2r-db]))

(deftest saves-a-learning-in-db
  (with-redefs-fn {#'r2r-db/insert (fn [documents learning]
                                     (println "insied insert")
                                     (is (= "learnings" documents))
                                     (is (= {:question "test question" :answer "test answer"} learning))
                                     1234)
                   #'r2r-db/insert-revision-backlog (fn [documents learning-id]
                                                      (println "Inside insert revision backlog")
                                                      (is (= 1234 learning-id))
                                                      (is (= "revision" documents)))}

    #(add-learning {:question "test question" :answer "test answer"})))

