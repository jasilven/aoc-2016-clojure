(ns day23-test
  (:require [day23 :refer [solve1]]
            [clojure.test :refer [deftest testing is]]))

(deftest day23-test
  (testing "part 1"
    (is (= 3 (solve1 "resources/day23-test-input.txt" {:a 0 :b 0 :c 0 :d 0})))))
