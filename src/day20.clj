(ns day20)

(defn parse-input [fname]
  (with-open [rdr (clojure.java.io/reader fname)]
    (->> (for [l (line-seq rdr)]
           (mapv #(Long/parseLong %) (re-seq #"\d+" l)))
         vec)))

(defn solve1 [fname]
  (let [rs (parse-input fname)]
    (loop [low -1]
      (let [nlow (second (apply max-key second
                                (filter #(<= (first %) (inc low)) rs)))]
        (if (= low nlow)
          (inc low)
          (recur nlow))))))

(comment
  (solve1 "resources/day20-input.txt")
  ;; correct answer 14975795

  )

(defn -main []
  (println "Part 1:" (solve1 "resources/day20-input.txt")))
