(ns day8
  (:require [clojure.string :as str]))

(defn fname->instructions [fname]
  (for [line (str/split-lines (str/trim (slurp fname)))
        :let [line (str/replace line "rotate " "")
              [a b] (mapv #(Integer/parseInt %) (re-seq #"\d+" line))]]
    (hash-map :oper (first (str/split line #" "))
              :a a
              :b b)))

(defn rect [screen a b]
  (->> (for [x (range a)
             y (range b)]
         (vector x y))
       (reduce #(conj %1 %2) screen)))

(defn rotate-row [screen w a b]
  (let [result (into #{} (remove #(= (second %) a) screen))]
    (->> (filter #(= (second %) a) screen)
         (map #(vector (mod (+ b (first %)) w)
                       (second %)))
         (into result))))

(defn rotate-column [screen h a b]
  (let [result (into #{} (remove #(= (first %) a) screen))]
    (->> (filter #(= (first %) a) screen)
         (map #(vector (first %)
                       (mod (+ b (second %)) h)))
         (into result))))

(defn print-screen [screen w h]
  (doseq [y (range h)]
    (doseq [x (range w)]
      (if (screen [x y])
        (print "#")
        (print " ")))
    (println)))

(defn execute [screen w h {:keys [oper a b]}]
  (case oper
    "rect" (rect screen a b)
    "column" (rotate-column screen h a b)
    "row" (rotate-row screen w a b)
    :else (throw (Exception. (str "unknown oper:" oper)))))

(defn solve1 [w h fname]
  (loop [[instruction & instructions] (fname->instructions fname)
         screen #{}]
    (if (nil? instruction)
      (count screen)
      (recur instructions
             (execute screen w h instruction)))))

(defn solve2 [w h fname]
  (loop [[instruction & instructions] (fname->instructions fname)
         screen #{}]
    (if (nil? instruction)
      (print-screen screen w h)
      (recur instructions
             (execute screen w h instruction)))))

(comment
  (solve1 50 6 "resources/day8-input.txt")
  ;; correct answer 115
  (solve2 50 6 "resources/day8-input.txt")
  ;; correct answer EFEYKFRFIJ
  )

(defn -main []
  (println "part 1:" (solve1 50 6 "resources/day8-input.txt"))
  (println "part 2:")
  (solve2 50 6 "resources/day8-input.txt"))
