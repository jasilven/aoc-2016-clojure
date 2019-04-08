(ns day13
  (:require [clojure.string :as str])
  (:require [clojure.data.priority-map :as pm]))

(defn open? [[x y] favourite]
  (let [num (+ (Math/pow x 2)
               (* 3 x)
               (* 2 x y)
               y
               (Math/pow y 2)
               favourite)]
    (-> (Integer/toString num 2)
        (str/replace "0" "")
        count
        even?)))

(defn neighbours [[x y] favourite visited]
  (->> (list [(dec x) y] [(inc x) y] [x (inc y)] [x (dec y)])
       (filter #(and (>= (first %) 0)
                     (>= (second %) 0)
                     (open? % favourite)
                     (nil? (visited %))))))

(defn update-edges [edges distance neighbours]
  (loop [edges edges
         [xy & xys] neighbours]
    (if (nil? xy)
      edges
      (recur (conj edges [xy (inc distance)])
             xys))))

(defn solve1
  "use dijkstra's algorithm to find shortes path from start to target"
  [start target favourite]
  (loop [edges (pm/priority-map start 0)
         visited #{}]
    (let [xy (first (remove visited (keys edges)))]
      (if (= target xy)
        (get edges xy)
        (recur (update-edges edges
                             (get edges xy)
                             (neighbours xy favourite visited))
               (conj visited xy))))))

(defn solve2 [start favourite]
  (loop [edges (pm/priority-map start 0)
         visited #{}]
    (let [cands (filter #(<= (second %) 50) edges)
          xy (first (remove visited (map first cands)))]
      (if (nil? xy)
        (count visited)
        (recur (update-edges edges
                             (get edges xy)
                             (neighbours xy favourite visited))
               (conj visited xy))))))

(comment
  (solve1 [1 1] [31 39] 1358)
  ;; correct answer 96
  (solve2 [1 1] 1358)
  ;; correct answer 141
  )

(defn -main []
  (println "part 1:" (solve1 [1 1] [31 39] 1358))
  (println "part 2:" (solve2 [1 1] 1358)))
