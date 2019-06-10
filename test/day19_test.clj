(ns day19-test
  (:require [day19 :refer [solve1]]
            [clojure.test :refer [deftest testing is]]))

(deftest day19-test
  (testing "part 1"
    (is (= 3 (solve1 3)))
    (is (= 3 (solve1 5)))
    (is (= 7 (solve1 7)))
    (is (= 3 (solve1 9)))
    (is (= 7 (solve1 11)))
    (is (= 11 (solve1 13)))
    (is (= 15 (solve1 15)))
    (is (= 3 (solve1 17)))))

