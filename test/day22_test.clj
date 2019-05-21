(ns day22-test
  (:require [day22 :refer :all]
            [clojure.test :refer :all]))

(deftest day22-test
  (testing "part 1"
    (is (= 7 (solve1 "resources/day22-test-input.txt")))))
