(ns day14-test
  (:require [day14 :refer [solve]]
            [clojure.test :refer [deftest testing is]]))

(deftest day14-test
  (testing "part 1"
    (is (= (solve "abc" 1) 22728))))
