(ns day24-test
  (:require [day24 :refer [solve1 parse-routemap]]
            [clojure.test :refer [deftest testing is]]))

(deftest day24-test
  (testing "part 1"
    (is (= 14 (solve1 (parse-routemap "resources/day24-test-input.txt"))))))
