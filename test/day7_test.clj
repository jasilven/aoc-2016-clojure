(ns day7-test
  (:require [day7 :refer :all]
            [clojure.test :refer :all]))

(deftest day7-test
  (testing "part 1"
    (is (= 3 (solve1 "resources/day7-test-input.txt"))))
  (testing "part 2"
    (is ( = 3 (solve2 "resources/day7-test-input2.txt")))))
