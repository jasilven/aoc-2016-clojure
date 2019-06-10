(ns day12-test
  (:require [day12 :refer [solve]]
            [clojure.test :refer [deftest testing is]]))

(deftest day12-test
  (testing "part 1"
    (is (= 42 (solve "resources/day12-test-input.txt" {:a 0 :b 0 :c 0 :d 0})))))
