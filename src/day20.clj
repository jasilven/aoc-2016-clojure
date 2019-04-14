(ns day20)

(def max-ip 4294967295)

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

(defn allowed? [ip rs]
  (reduce (fn [result [low high]]
            (if (and (<= ip high) (>= ip low))
              (reduced false) result)) true rs))

(defn count-allowed [high rs]
  (loop [ip (inc high)
         done false
         result 0]
    (if (or done (> ip max-ip)) result
        (if (allowed? ip rs)
          (recur (inc ip) false (inc result))
          (recur ip true result)))))

(defn solve2 [fname]
  (let [rs (parse-input fname)
        highs (dedupe (map second rs))]
    (reduce #(+ %1 (count-allowed %2 rs)) 0 highs)))

(comment
  (solve1 "resources/day20-input.txt")
  ;; correct answer 14975795
  (solve2 "resources/day20-input.txt")
  ;; correct answer 101
  )

(defn -main []
  (println "Part 1:" (solve1 "resources/day20-input.txt"))
  (println "Part 2:" (solve2 "resources/day20-input.txt")))
