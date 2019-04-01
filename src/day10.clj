(ns day10
  (:require [clojure.string :as str]))

(defn update-bots
  "parse 'value...' from line and return updated bots map"
  [bots line]
  (let [[value bot] (mapv #(Integer/parseInt %) (re-seq #"\d+" line))
        old-value (get bots bot (list))]
    (assoc bots bot (conj old-value value))))

(defn update-rules
  "parse '...rules...' from line and return updated rules map"
  [rules line]
  (let [[bot low-no hi-no] (mapv #(Integer/parseInt %)
                                 (re-seq #"\d+" line))]
    (assoc rules bot (hash-map :low-no low-no
                               :hi-no hi-no
                               :low-target (keyword (second (re-find #"low to (\w+)" line)))
                               :hi-target (keyword (second (re-find #"high to (\w+)" line)))))))

(defn parse-lines
  "parse file and return map containing :bots and :rules as map"
  [fname]
  (let [lines (str/split-lines (str/trim (slurp fname)))]
    (loop [[line & lines] lines
           bots {}
           rules {}]
      (if (nil? line)
        (hash-map :bots bots :rules rules)
        (cond
          (str/starts-with? line "value")
          (recur lines
                 (update-bots bots line)
                 rules)

          (str/starts-with? line "bot")
          (recur lines
                 bots
                 (update-rules rules line))

          :else (throw (Exception. (str "Unknown line:" line))))))))

(defn apply-rule [result num target value]
  (if (= :bot target)
    (update-in result [:bots num] conj value)
    (assoc-in result [:outputs num] value)))

(defn apply-rules [rules bot {:keys [outputs bots] :as result}]
  (let [rule (get rules (first bot))
        {:keys [low-no hi-no low-target hi-target]} rule]
    (-> result
        (apply-rule low-no low-target (apply min (second bot)))
        (apply-rule hi-no hi-target (apply max (second bot)))
        (assoc-in [:bots (first bot)] (list)))))

(defn solve1 [fname]
  (let [{:keys [bots rules]} (parse-lines fname)]
    (loop [{:keys [bots outputs] :as result} {:bots bots :outputs {}}]
      (let [bot (first (filter #(> (count (second %)) 1) bots))]
        (if (= '(17 61) (sort (second bot)))
          (first bot)
          (recur (apply-rules rules bot result)))))))

(defn solve2 [fname]
  (let [{:keys [bots rules]} (parse-lines fname)]
    (loop [{:keys [bots outputs] :as result} {:bots bots :outputs {}}]
      (let [bot (first (filter #(> (count (second %)) 1) bots))]
        (if (nil? bot)
          (* (get outputs 0) (get outputs 1) (get outputs 2))
          (recur (apply-rules rules bot result)))))))

(comment
  (solve1 "resources/day10-input.txt")
  ;; correct answer 141
  (solve2 "resources/day10-input.txt")
  ;; correct answer 1209
  )

(defn -main []
  (println "part 1:" (solve1 "resources/day10-input.txt"))
  (println "part 2:" (solve2 "resources/day10-input.txt")))
