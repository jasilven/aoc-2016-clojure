(ns day5-test
  (:require [day5 :refer [solve1 solve2]]
            [clojure.test :refer [deftest testing is]]))

(deftest day5-test
  (testing "part 1"
    (is (= "18f47a30" (solve1 "abc"))))
  (testing "part 2"
    (is (= "05ace8e3" (solve2 "abc")))))

