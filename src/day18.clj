(ns day18
  (:require [clojure.string :as str]))

(defn new-tile [prev3]
  (case prev3
    "^^." \^
    ".^^" \^
    "^.." \^
    "..^" \^
    \.))

(defn next-row [row]
  (->> (str \. row \.)
       (partition 3 1)
       (map #(apply str %))
       (map new-tile)
       (apply str)))

(defn char-count [ch s]
  (reduce #(if (= %2 ch) (inc %1) %1) 0 s))

(defn solve [fname row-count]
  (loop [row (str/trim (slurp fname))
         result (char-count \. row)
         cnt 1]
    (if (>= cnt row-count) result
        (let [new-row (next-row row)]
          (recur new-row
                 (+ result (char-count \. new-row))
                 (inc cnt))))))

(comment
  (solve "resources/day18-input.txt" 40)
  ;; correct answer 1956
  (solve "resources/day18-input.txt" 400000)
  ;; correct answer 19995121
  )

(defn -main []
  (println "Part 1:" (solve "resources/day18-input.txt" 40))
  (println "Part 2:" (solve "resources/day18-input.txt" 400000)))
