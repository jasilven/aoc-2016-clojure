(ns day8-test
  (:require [day8 :refer :all]
            [clojure.test :refer :all]))

(deftest day8-test
  (testing "part 1"
    (is (= 6 (solve1 7 3 "resources/day8-test-input.txt")))))
