(ns day16-test
  (:require [day16 :refer [solve]]
            [clojure.test :refer [deftest testing is]]))

(deftest day16-test
  (testing "part 1"
    (is (= "01100" (solve "10000" 20)))))
