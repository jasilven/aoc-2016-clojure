(ns day6
  (:require [clojure.string :as str]))

(defn solve [fname comp-fn]
  (->> (str/split-lines (str/trim (slurp fname)))
       (mapcat #(zipmap (range) %))
       (group-by first)
       (sort-by first)
       (map #(first (sort-by second comp-fn (frequencies (second %)))))
       (map #(second (first %)))
       (apply str)))

(comment
  (solve "resources/day6-input.txt" >)
  ;; correct answer nabgqlcw
  (solve "resources/day6-input.txt" <)
  ;; correct answer ovtrjcjh
  )

(defn -main []
  (println "part 1:" (solve "resources/day6-input.txt" >))
  (println "part 2:" (solve "resources/day6-input.txt" <)))
