(ns day24
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [clojure.data.priority-map :as pm]
            [clojure.set :as set]))

(defn parse-routemap [fname]
  (let [lines (str/split-lines (str/trim (slurp fname)))]
    (->> (for [y (range (count lines))
               x (range (-> lines first count))
               :let [xy (-> lines (get y) (get x) str)]
               :when (not= xy "#")]
           (hash-map [x y] xy))
         (apply merge))))

(defn all-routes [routemap]
  (->> routemap
       (remove #(= "." (val %)))
       combo/permutations
       (filter #(= (val (first %)) "0"))
       (map #(mapv first %))))

(defn adjacent [routemap xy]
  (->> [[-1 0] [1 0] [0 1] [0 -1]]
       (map #(mapv + xy %))
       (remove #(nil? (get routemap %)))
       (into #{})))

(defn steps [routemap [a b]]
  (let [adj (adjacent routemap a)
        adj+steps (->> (repeat (count adj) 1) (interleave adj) (partition 2))]
    (loop [seen #{a}
           unseen (into (pm/priority-map) adj+steps)]
      (let [[xy steps] (first unseen)]
        (if (= xy b) steps
            (let [unseen (dissoc unseen xy)
                  nexts (set/difference (adjacent routemap xy) seen)
                  nexts+steps (->> (inc steps) (repeat (count nexts)) (interleave nexts))]
              (recur (conj seen xy)
                     (into unseen (partition 2 nexts+steps)))))))))

(def steps-memo (memoize steps))

(defn steps-count [routemap route]
  (->> (partition 2 1 route)
       (map #(steps-memo routemap (sort %)))
       (reduce +)))

(defn solve1 [fname]
  (let [routemap (parse-routemap fname)]
    (->> (all-routes routemap)
         (map #(steps-count routemap %))
         (apply min))))

(defn solve2 [fname]
  (let [routemap (parse-routemap fname)]
    (->> (all-routes routemap)
         (map #(conj % (first %)))
         (map #(steps-count routemap %))
         (apply min))))

(comment
  (time (solve1 "resources/day24-input.txt"))
  ;; correct answer 460
  (time (solve2 "resources/day24-input.txt"))
  ;; correct answer 668
  )

(defn -main []
  (println "Part 1:" (solve1 "resources/day24-input.txt"))
  (println "Part 2:" (solve2 "resources/day24-input.txt")))
