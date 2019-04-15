(ns day21-test
  (:require [day21 :refer :all]
            [clojure.test :refer :all]))

(deftest day21-test
  (testing "commands"
    (is (= "maikko" (swap-ch "moikka" [\o \a])))
    (is (= "kkamoi" (rotate-lr "moikka" [:right 3])))
    (is (= "mokika" (reverse-positions "moikka" [2 3])))
    (is (= "akkiom" (reverse-positions "moikka" [0 5])))
    (is (= "bdeac" (move-position "bcdea" [1 4])))
    (is (= "ecabd" (rotate-based-position "abdec" [\b])))
    (is (= "aoikkm" (swap-pos "moikka" [0 5]))))
  (testing "part 1"
    (is (= "decab" (solve1 "resources/day21-test-input.txt" "abcde")))))
