(ns day3-test
  (:require [day3 :refer :all]
            [clojure.test :refer :all]))

(deftest day3-test
  (testing "part 2"
    (is (= 6 (solve2 "resources/day3-test-input.txt")))))
