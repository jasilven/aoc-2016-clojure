(ns day19)

(defn drop-first-if-odd-cnt [cur coll]
  (cond
    (= 1 (count coll)) coll
    (odd? (count cur)) (rest coll)
    :default coll))

(defn update-elves [coll]
  (->> (partition 2 2 -1 coll)
       (map first)
       (drop-first-if-odd-cnt coll)))

(defn solve1 [cnt]
  (loop [elves (vec (range 1 (inc cnt)))]
    (if (= (count elves) 1)
      (first elves)
      (recur (update-elves elves)))))

(defn remove-elf-across [cur coll]
  (let [cnt (count coll)
        i (mod (+ cur (/ cnt 2)) cnt)]
    (into (subvec coll 0 i)
          (subvec coll (inc i)))))

(defn solve2 [cnt]
  (loop [elves (vec (range 1 (inc cnt)))
         i 0]
    (if (= (count elves) 1)
      (first elves)
      (recur (remove-elf-across i elves)
             (mod (inc i) (count elves))))))

(comment
  (solve1 3014603)
  ;; correct answer 1834903
  (solve2 3014603)
  ;; TODO: must be optimized! this is slow as hell.
  )

(defn -main []
  (println "Part 1:" (solve1 3014603)))
