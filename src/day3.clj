(ns day3
  (:require [clojure.string :as str]))

(defn fname->triangles [fname]
  (for [line (str/split-lines (str/trim (slurp fname)))]
    (->> (re-seq #"\d+" line)
         (mapv #(Integer/parseInt %)))))

(defn triangle? [[a b c]]
  (and (> (+ a b) c)
       (> (+ a c) b)
       (> (+ b c) a)))

(defn solve1 [fname]
  (count (filter triangle? (fname->triangles fname))))

(defn third [coll] (nth coll 2))

(defn solve2 [fname]
  (let [triangles1 (fname->triangles fname)]
    (->> (lazy-cat (map first triangles1)
                   (map second triangles1)
                   (map third triangles1))
         (partition 3)
         (map #(vector (first %) (second %) (third %)))
         (filter triangle?)
         count)))

(comment
  (solve1 "resources/day3-input.txt")
  ;; correct answer 917
  (solve2 "resources/day3-input.txt")
  ;; correct answer 1649
  )

(defn -main []
  (println "part 1:" (solve1 "resources/day3-input.txt"))
  (println "part 2:" (solve2 "resources/day3-input.txt")))
