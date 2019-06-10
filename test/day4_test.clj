(ns day4-test
  (:require [day4 :refer [solve1]]
            [clojure.test :refer [deftest testing is]]))

(deftest day4-test
  (testing "part 1"
    (is (= 1514 (solve1 "resources/day4-test-input.txt")))))

