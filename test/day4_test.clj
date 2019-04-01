(ns day4-test
  (:require [day4 :refer :all]
            [clojure.test :refer :all]))

(deftest day4-test
  (testing "part 1"
    (is (= 1514 (solve1 "resources/day4-test-input.txt")))))

