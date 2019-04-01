(ns day17-test
  (:require [day17 :refer :all]
            [clojure.test :refer :all]))

(deftest day17-test
  (testing "part 1"
    (is (= "DDRRRD" (solve1 "ihgpwlah")))
    (is (= "DDUDRLRRUDRD" (solve1 "kglvqrro")))
    (is (= "DRURDRUDDLLDLUURRDULRLDUUDDDRR" (solve1 "ulqzkmiv")))))
