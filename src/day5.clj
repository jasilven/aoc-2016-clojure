(ns day5
  (:import [java.security MessageDigest] [java.math BigInteger])
  (:require [clojure.string :as str]))

(defn md5 [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

(defn valid? [s]
  (str/starts-with? s "00000"))

(defn solve1 [doorID]
  (->> (for [n (range)] (md5 (str doorID n)))
       (filter valid?)
       (take 8)
       (map #(nth % 5))
       (apply str)))

(defonce digits (into #{} (map #(first (str %)) (range 8))))

(defn valid2? [s]
  (and (valid? s)
       (contains? digits (nth s 5))))

(defn solve2 [doorID]
  (loop [n 0
         result {}]
    (if (= 8 (count result))
      (->> (map second (sort-by first result))
           (apply str))
      (let [h (md5 (str doorID n))]
        (if (and (valid2? h)
                 (nil? (get result (nth h 5))))
          (recur (inc n) (assoc result (nth h 5) (nth h 6)))
          (recur (inc n) result))))))

(comment
  (solve1 "cxdnnyjw")
  ;; correct answer f77a0e6e
  (solve2 "cxdnnyjw")
  ;; correct answer 999828ec
  )

(defn -main []
  (println "part 1:" (solve1 "cxdnnyjw"))
  (println "part 2:" (solve2 "cxdnnyjw")))
