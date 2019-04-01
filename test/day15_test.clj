(ns day15-test
  (:require [day15 :refer :all]
            [clojure.test :refer :all]))

(deftest day15-test
  (testing "part 1"
    (is (= 5 (solve1 "resources/day15-test-input.txt")))))
