(ns day24-test
  (:require [day24 :refer :all]
            [clojure.test :refer :all]))

(deftest day24-test
  (testing "part 1"
    (is (= 14 (solve1 "resources/day24-test-input.txt")))))
