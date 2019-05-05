(ns day9
  (:require [clojure.string :as str]))

(defn starts-with-marker? [line]
  (some? (re-find #"^\(\d+x\d+\)" (apply str line))))

(defn parse-marker [line]
  (->> (re-find #"^\(\d+x\d+\)" line)
       (re-seq #"\d+")
       (mapv #(Integer/parseInt %))))

(defn drop-marker [line]
  (str/replace-first line #"\(\d+x\d+\)" ""))

(defn process-prefix [{:keys [line result]}]
  (let [prefix (re-find #"^\w+" line)]
    (hash-map :line (subs line (count prefix))
              :result (+ result (count prefix)))))

(defn process-marker [{:keys [line result]}]
  (let [[len n] (parse-marker line)
        line (drop-marker line)]
    (hash-map :line (subs line len)
              :result (+ result (* n len)))))

(defn solve1 [line]
  (loop [{:keys [line result] :as all} {:line line :result 0}]
    (cond
      (empty? line) result
      (starts-with-marker? line) (recur (process-marker all))
      :else (recur (process-prefix all)))))

(defn parse-marker2 [line]
  (when-let [[_ marker & args] (re-matches #"(\((\d+)x(\d+)\)).*" (apply str line))]
    [(mapv #(Integer/parseInt %) args) (drop (count marker) line)]))

(defn has-marker? [line]
  (re-find #"(\((\d+)x(\d+)\)).*" (apply str line)))

(defn solve2 [line]
  (loop [result 0
         line line]
    (cond
      (empty? line) result
      (starts-with-marker? line)
      (when-let [[[cnt r] s] (parse-marker2 line)]
        (let [[start end] (split-at cnt s)]
          (recur (+ result
                    (* r (if (has-marker? start)
                           (solve2 start)
                           (count start))))
                 end)))
      :else (recur (inc result) (rest line)))))

(comment
  (solve1 (str/trim (slurp "resources/day9-input.txt")))
  ;; correct answer 120765
  (solve2 (str/trim (slurp "resources/day9-input.txt")))
  ;; correct answer 11658395076
  )

(defn -main []
  (println "part 1:" (solve1 (str/trim (slurp "resources/day9-input.txt"))))
  (println "part 2:" (solve2 (str/trim (slurp "resources/day9-input.txt")))))
