(ns day18-test
  (:require [day18 :refer :all]
            [clojure.test :refer :all]))

(deftest day18-test
  (testing "part 1"
    (is (= 6 (solve "resources/day18-test1-input.txt" 3)))
    (is (= 38 (solve "resources/day18-test2-input.txt" 10)))))
