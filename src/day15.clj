(ns day15
  (:require [clojure.string :as str]))

(defn slurp-discs [fname]
  (for [line (str/split-lines (str/trim (slurp fname)))]
    (-> (zipmap [:id :poscount :time :pos]
                (map #(Integer/parseInt %) (re-seq #"\d+" line)))
        (select-keys [:id :poscount :pos]))))

(defn update-pos [discs delta]
  (for [disc discs
        :let [{:keys [id poscount pos]} disc]]
    {:id id :poscount poscount :pos (mod (+ id delta pos) poscount)}))

(defn not-all-in-zero-pos? [discs]
  (not-every? #(zero? (:pos %)) discs))

(defn solve [discs]
  (->> (interleave (range) (repeat discs))
       (partition 2)
       (map #(vector (first %) (update-pos (second %)
                                           (first %))))
       (drop-while #(not-all-in-zero-pos? (second %)))
       ffirst))

(defn solve1 [fname]
  (solve (slurp-discs fname)))

(defn solve2 [fname]
  (let [discs (slurp-discs fname)]
    (solve (concat discs (list {:id (inc (count discs))
                                :poscount 11
                                :pos 0})))))
(comment
  (solve1 "resources/day15-input.txt")
  ;; correct answer 400589
  (solve2 "resources/day15-input.txt")
  ;; correct answer 3045959
  )

(defn -main []
  (println "part 1:" (solve1 "resources/day15-input.txt"))
  (println "part 2:" (solve2 "resources/day15-input.txt")))
