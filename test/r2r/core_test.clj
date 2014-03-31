(ns r2r.core-test
  (:require [clojure.test :refer :all]
            [r2r.controller :refer :all]
            [r2r.db.mongo :as r2r-db]))

(comment deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(deftest saves-a-learning-in-db
  (with-redefs-fn {#'r2r-db/insert (fn [documents learning]
                                     (is (= "learnings" documents))
                                     (is (= {:question "test question" :answer "test answer"} learning)))}
    #(add-learning {:question "test question" :answer "test answer"})))