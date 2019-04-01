(ns day14-test
  (:require [day14 :refer :all]
            [clojure.test :refer :all]))

(deftest day14-test
  (testing "part 1"
    (is (= (solve "abc" 1) 22728))))
