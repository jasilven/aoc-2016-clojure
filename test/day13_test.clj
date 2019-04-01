(ns day13-test
  (:require [day13 :refer :all]
            [clojure.test :refer :all]))

(deftest day13-test
  (testing "part 1"
    (is (= 11 (solve1 [1 1] [7 4] 10)))))
