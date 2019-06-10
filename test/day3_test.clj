(ns day3-test
  (:require [day3 :refer [solve2]]
            [clojure.test :refer [deftest testing is are]]))

(deftest day3-test
  (testing "part 2"
    (is (= 6 (solve2 "resources/day3-test-input.txt")))))
