(ns day15-test
  (:require [day15 :refer [solve1]]
            [clojure.test :refer [deftest testing is]]))

(deftest day15-test
  (testing "part 1"
    (is (= 5 (solve1 "resources/day15-test-input.txt")))))
