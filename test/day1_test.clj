(ns day1-test
  (:require [day1 :refer [solve1 next-pos]]
            [clojure.test :refer [deftest testing is]]))

(deftest day1-test
  (testing "part 1 next-pos"
    (is (= [-4 1 \W] (next-pos [1 1 \S] [\R 5])))
    (is (= [-4 1 \W] (next-pos [1 1 \S] [\R 5])))
    (is (= [-2 0 \W] (next-pos [0 0 \N] [\L 2])))
    (is (= [0 -2 \N] (next-pos [0 0 \W] [\R 2])))
    (is (= [1 6 \S] (next-pos [1 1 \E] [\R 5])))
    (is (= [2 0 \N] (next-pos [2 2 \E] [\L 2])))
    (is (= [1 0 \E] (next-pos [0 0 \N] [\R 1])))
    (is (= [-3 0 \W] (next-pos [0 0 \N] [\L 3])))
    (is (= [1 0 \E] (next-pos [0 0 \N] [\R 1])))
    (is (= [2 0 \E] (next-pos [0 0 \N] [\R 2])))
    (is (= [0 -2 \N] (next-pos [0 0 \W] [\R 2])))
    (is (= [0 0 \S] (next-pos [0 -2 \W] [\L 2]))))
  (testing "part 1 solve"
    (is (= 12 (solve1 "resources/day1-test-input.txt")))
    (is (= 2 (solve1 "resources/day1-test-input2.txt")))
    (is (= 0 (solve1 "resources/day1-test-input4.txt")))
    (is (= 5 (solve1 "resources/day1-test-input3.txt")))))
