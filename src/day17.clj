(ns day17
  (:import (java.security MessageDigest)))

(def directions [[0 -1] [0 1] [-1 0] [1 0]])
(def letters [\U \D \L \R])
(defonce moves (zipmap directions letters))

(defn md5 [^String s]
  (->> (.getBytes s)
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)
       (format "%032x")))

(defn out-of-grid? [pos dir [mx my]]
  (let [[x y] (mapv + pos dir)]
    (or (< x 0) (< y 0)
        (>= x mx) (>= y my))))

(defn open-dirs [code pos grid]
  (->> (subs (md5 code) 0 4)
       (map #{\b \c \d \e \f})
       (zipmap directions)
       (remove #(nil? (val %)))
       (map key)
       (remove #(out-of-grid? pos % grid))))

(defn new-state [{:keys [pos code]} dir grid]
  (let [p (mapv + pos dir)
        code (str code (moves dir))]
    {:pos p
     :code code
     :dirs (open-dirs code p grid)}))

(defn solve [code pos target grid result-fn]
  (loop [[state & states] [{:pos pos
                            :code code
                            :dirs (open-dirs code pos grid)}]
         result ""]
    (if (empty? state)
      (subs result (count code))
      (let [{:keys [pos code dirs]} state
            [d & ds] dirs]
        (cond
          (= target pos)
          (recur states
                 (if (empty? result) code (result-fn count result code)))

          (nil? d)
          (recur states result)

          :else
          (recur (conj states
                       (assoc state :dirs ds)
                       (new-state state d grid))
                 result))))))

(defn solve1 [code]
  (solve code [0 0] [3 3] [4 4] min-key))

(defn solve2 [code]
  (count (solve code [0 0] [3 3] [4 4] max-key)))

(comment
  (solve1 "vwbaicqe")
  ;; correct answer DRDRULRDRD
  (solve2 "vwbaicqe")
  ;; correct answer 384
  )

(defn -main []
  (println "part 1:" (solve1 "vwbaicqe"))
  (println "part 2:" (solve2 "vwbaicqe")))
