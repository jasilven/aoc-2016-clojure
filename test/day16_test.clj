(ns day16-test
  (:require [day16 :refer :all]
            [clojure.test :refer :all]))

(deftest day16-test
  (testing "part 1"
    (is (= "01100" (solve "10000" 20) ))))
