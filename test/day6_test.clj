(ns day6-test
  (:require [day6 :refer :all]
            [clojure.test :refer :all]))

(deftest day6-test
  (testing "part 1"
    (is (= "easter" (solve "resources/day6-test-input.txt" >))))
  (testing "part 2"
    (is (= "advent" (solve "resources/day6-test-input.txt" <)))))
