(ns day17-test
  (:require [day17 :refer [solve1]]
            [clojure.test :refer [deftest testing is]]))

(deftest day17-test
  (testing "part 1"
    (is (= "DDRRRD" (solve1 "ihgpwlah")))
    (is (= "DDUDRLRRUDRD" (solve1 "kglvqrro")))
    (is (= "DRURDRUDDLLDLUURRDULRLDUUDDDRR" (solve1 "ulqzkmiv")))))
