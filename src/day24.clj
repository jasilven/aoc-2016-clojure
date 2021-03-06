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
  (loop [seen #{a}
         unseen (pm/priority-map a 0)]
    (let [[cur steps] (first unseen)]
      (if (= cur b) steps
          (let [unseen (dissoc unseen cur)
                nexts (set/difference (adjacent routemap cur) seen)]
            (recur (conj seen cur)
                   (into unseen (zipmap nexts (repeat (inc steps))))))))))

(def steps-memo (memoize steps))

(defn steps-count [routemap route]
  (->> (partition 2 1 route)
       (map #(steps-memo routemap (sort %)))
       (reduce +)))

(defn solve1 [routemap]
  (->> (all-routes routemap)
       (map #(steps-count routemap %))
       (apply min)))

(defn solve2 [routemap]
  (->> (all-routes routemap)
       (map #(conj % (first %)))
       (pmap #(steps-count routemap %))
       (apply min)))

(comment
  (time (solve1 (parse-routemap "resources/day24-input.txt")))
  ;; correct answer 460
  (time (solve2 (parse-routemap "resources/day24-input.txt")))
  ;; correct answer 668
  )

(defn -main []
  (let [routemap (parse-routemap "resources/day24-input.txt")]
    (println "Part 1:" (solve1 routemap))
    (println "Part 2:" (solve2 routemap))
    (shutdown-agents)))
