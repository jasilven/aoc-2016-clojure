(ns day22
  (:require [clojure.java.io :as io]
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
          (let [[a b] n]
            (recur ns
                   (if (or (viable? a b) (viable? b a))
                     (inc cnt) cnt)))))))

(defn solve2 [fname]
  (let [nodes (parse-input fname)
        large-limit (:size (apply min-key :used (vals nodes)))
        max-x (:x (apply max-key :x (vals nodes)))]
    (doseq [y (range (inc (:y (apply max-key :y (vals nodes)))))]
      (doseq [x (range (inc max-x))]
        (let [used (:used (get nodes [x y]))]
          (cond
            (and (= x max-x) (zero? y)) (print "G")
            (and (zero? x) (zero? y)) (print "H")
            (zero? used) (print "_")
            (<= used large-limit) (print ".")
            :else (print "#"))))
      (println)))
  (println "Play sliding puzzle and count the steps:")
  (println "(+ 17 22 34 (* 34 5) 1) =" (+ 17 22 34 (* 34 5) 1))
  (+ 17 22 34 (* 34 5) 1))

(comment
  (solve1 "resources/day22-input.txt")
  ;; correct answer 864
  (solve2 "resources/day22-input.txt")
  ;; correct answer 244
  )

(defn -main []
  (println "Part 1:" (solve1 "resources/day22-input.txt"))
  (println "Part 2:" (solve2 "resources/day22-input.txt")))
