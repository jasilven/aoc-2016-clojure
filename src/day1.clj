(ns day1 (:require [clojure.string :as str]
                   [clojure.set :as set]))

(defn fname->turns [fname]
  (for [move (str/split (str/trim (slurp fname)) #", ")]
    (vector (first move) (Integer/parseInt (subs move 1)))))

(defn distance [p1 p2]
  (reduce + (map #(Math/abs (- %2 %1)) p1 p2)))

(defn next-pos [[x y facing] [direction steps]]
  (case [facing direction]
    [\N \L] [(- x steps) y \W]
    [\N \R] [(+ x steps) y \E]
    [\E \L] [x (- y steps) \N]
    [\E \R] [x (+ y steps) \S]
    [\S \L] [(+ x steps) y \E]
    [\S \R] [(- x steps) y \W]
    [\W \L] [x (+ y steps) \S]
    [\W \R] [x (- y steps) \N]))

(defn solve1 [fname]
  (loop [[x y _ :as curpos] [0 0 \N]
         [turn & turns] (fname->turns fname)]
    (if (nil? turn)
      (distance [0 0] [x y])
      (recur (next-pos curpos turn)
             turns))))

(defn path
  "seq of positions from [x1 y1] to [x2 y2] in stepping order and not including [x2 y2]"
  [[x1 y1] [x2 y2]]
  (if (= x1 x2)
    (for [y (range y1 y2 (if (> y2 y1) 1 -1))] (vector x1 y))
    (for [x (range x1 x2 (if (> x2 x1) 1 -1))] (vector x y1))))

(defn solve2 [fname]
  (loop [[x y _ :as curpos] [0 0 \N]
         [turn & turns] (fname->turns fname) seen #{}]
    (let [[x2 y2 _ :as npos] (next-pos curpos turn)
          steps (path [x y] [x2 y2])]
      (if-let [seen-pos (first (filter #(contains? seen %) steps))]
        (distance [0 0] seen-pos)
        (recur npos
               turns
               (into seen steps))))))

(comment
  (solve1 "resources/day1-input.txt")
 ;; correct answer 231
  (solve2 "resources/day1-input.txt")
  ;; correct answer 147
  )

(defn -main []
  (println "part 1:" (solve1 "resources/day1-input.txt"))
  (println "part 2:" (solve2 "resources/day1-input.txt")))
