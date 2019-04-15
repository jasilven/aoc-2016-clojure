(ns day22
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(defn parse-ints [s] (mapv #(Integer/parseInt %) (re-seq #"\d+" s)))

(defn parse-input [fname]
  (with-open [r (io/reader fname)]
    (->> (for [line (drop 2 (line-seq r))]
           (zipmap [:x :y :size :used :avail :percent] (parse-ints line)))
         (map #(vector [(:x %) (:y %)] %))
         (into {}))))

(defn viable? [a b]
  (and (> (:used a) 0)
       (not (and (= (:x a) (:x b)) (= (:y a) (:y b))))
       (<= (:used a) (:avail b))))

(defn solve1 [fname]
  (let [nodes (parse-input fname)]
    (loop [[n & ns] (combo/combinations (vals nodes) 2)
           cnt 0]
      (if (nil? n) cnt
          (let [a (first n)
                b (second n)]
            (recur ns
                   (if (or (viable? a b) (viable? b a))
                     (inc cnt) cnt)))))))

(comment
  (solve1 "resources/day22-input.txt")
  ;; correct answer 864

  )

(defn -main []
  (println "Part 1:" (solve1 "resources/day22-input.txt")))
