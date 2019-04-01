(ns day2
  (:require [clojure.string :as str]))

(defn keypad [size]
  (->> (for [row (range size)
             col (range size)] (vector col row))
       (interleave (range 1 (inc (* size size))))
       (partition 2)
       (map #(vector (second %) (first %)))
       (into {})))

(defn move-point [ch point kpad]
  (let [[x y] point
        new-point (case ch
                    \U [x (dec y)]
                    \D [x (inc y)]
                    \L [(dec x) y]
                    \R [(inc x) y])]
    (if (contains? kpad new-point)
      new-point
      point)))

(defn solve [fname kpad start-pos]
  (loop [[line & lines] (str/split-lines (str/trim (slurp fname)))
         result [start-pos]]
    (if (nil? line)
      (apply str (map kpad (drop 1 result)))
      (recur lines
             (conj result (reduce #(move-point %2 %1 kpad) (last result) line))))))

(defn distance [p1 p2]
  (reduce + (map #(Math/abs (- %2 %1)) p1 p2)))

(defn keypad2 [size]
  (let [half-size (int (/ size 2))]
    (->> (for [row (range (inc size))
               col (range (inc size))]
           (if (<= (distance [half-size half-size] [col row]) half-size)
             [col row]))
         (remove nil?)
         (interleave [1 2 3 4 5 6 7 8 9 \A \B \C \D])
         (partition 2)
         (map #(vector (second %) (first %)))
         (into {}))))

(comment
  (solve "resources/day2-input.txt" (keypad 3) [1 1])
  ;; correct answer 74921
  (solve "resources/day2-input.txt" (keypad2 5) [0 2])
  ;; correct answer A6B35
  )

(defn -main []
  (println "part 1:" (solve "resources/day2-input.txt" (keypad 3) [1 1]))
  (println "part 2:" (solve "resources/day2-input.txt" (keypad2 5) [0 2])))
