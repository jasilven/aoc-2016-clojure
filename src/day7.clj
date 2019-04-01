(ns day7
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn fname->ips [fname]
  (for [line (str/split-lines (str/trim (slurp fname)))
        :let [brackets (into [] (re-seq #"\[\w+\]" line))]]
    (hash-map :plain (str/split line #"\[\w+\]")
              :brackets (map #(str/replace % #"[\[\]]" "") brackets))))

(defn palindromes [s cnt]
  (->> (partition cnt 1 s)
       (filter #(= % (reverse %)))
       (remove #(= (nth % 0) (nth % 1)))))

(defn palindrome? [s cnt]
  (-> (palindromes s cnt) (not-empty)))

(defn supports-tsl? [ip]
  (let [cnt 4]
    (and (not-any? #(palindrome? % cnt) (:brackets ip))
         (some #(palindrome? % cnt) (:plain ip)))))

(defn solve1 [fname]
  (->> (fname->ips fname)
       (filter supports-tsl?)
       count))

(defn aba->bab [s]
  (str (nth s 1) (nth s 0) (nth s 1)))

(defn abas [coll]
  (mapcat #(palindromes % 3) coll))

(defn supports-ssl? [ip]
  (let [ps (into #{} (map #(apply str %) (abas (:plain ip))))
        rps (into #{} (map aba->bab (abas (:brackets ip))))]
    (->> (set/intersection ps rps)
         (not-empty))))

(defn solve2 [fname]
  (->> (fname->ips fname)
       (filter supports-ssl?)
       count))

(comment
  (solve1 "resources/day7-input.txt")
  ;; correct answer 118
  (solve2 "resources/day7-input.txt")
  ;; correct answer 260
  )

(defn -main []
  (println "part 1:" (solve1 "resources/day7-input.txt"))
  (println "part 2:" (solve2 "resources/day7-input.txt")))
