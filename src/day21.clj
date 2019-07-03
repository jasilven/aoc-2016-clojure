(ns day21
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(defn parse-ints [s] (mapv #(Integer/parseInt %) (re-seq #"\d+" s)))

(defn swap-pos [s [pos1 pos2]]
  (let [arr (char-array s)
        ch2 (aget arr pos2)]
    (aset arr pos2 (aget arr pos1))
    (aset arr pos1 ch2)
    (str/join arr)))

(defn swap-ch [s [ch1 ch2]]
  (-> (str/replace-first s ch1 \X)
      (str/replace-first ch2 ch1)
      (str/replace-first \X ch2)))

(defn rotate-lr [s [direction n]]
  (let [slen (count s)]
    (if-not (or (= n slen) (zero? n))
      (if (= direction :left)
        (subs (str s s) n (+ n slen))
        (subs (str s s) (- slen n) (- (* 2 slen) n)))
      s)))

(defn rotate-based-position [s [ch]]
  (let [i (str/index-of s ch)]
    (-> (rotate-lr s [:right (+ (inc i))])
        (rotate-lr [:right (if (>= i 4) 1 0)]))))

(defn reverse-positions [s [pos1 pos2]]
  (let [a (subs s 0 pos1)
        b (str/reverse (subs s pos1 (inc pos2)))
        c (subs s (inc pos2))]
    (str a b c)))

(defn move-position [s [pos1 pos2]]
  (let [spl (split-at pos1 s)
        ch (first (second spl))
        ss (str/join (concat (first spl) (rest (second spl))))
        spl2 (split-at pos2 ss)]
    (str/join (concat (first spl2) (list ch) (second spl2)))))

(defn parse-cmds [fname]
  (with-open [r (io/reader fname)]
    (->> (for [l (line-seq r)]
           (condp = (str/join " " (take 2 (str/split l #" ")))
             "swap position" {:command swap-pos
                              :args (parse-ints l)}
             "swap letter" {:command swap-ch
                            :args (mapv #(first (second %)) (re-seq #"letter (.)" l))}
             "rotate right" {:command rotate-lr
                             :args [:right (Integer/parseInt (re-find #"\d+" l))]}
             "rotate left" {:command rotate-lr
                            :args [:left (Integer/parseInt (re-find #"\d+" l))]}
             "rotate based" {:command rotate-based-position
                             :args [(last l)]}
             "reverse positions" {:command reverse-positions
                                  :args (parse-ints l)}
             "move position" {:command move-position
                              :args (parse-ints l)}
             :else
             (throw (.Exception "unknown command"))))
         vec)))

(defn solve1 [cmds s]
  (loop [[c & cs] cmds
         s s]
    (if (nil? c) s
        (let [{:keys [command args]} c]
          (recur cs (command s args))))))

(defn solve2 [cmds s1 s2]
  (->> (combo/permutations s1)
       (map #(apply str %))
       (pmap #(vector % (solve1 cmds %)))
       (drop-while #(not= (second %) s2))
       ffirst))

(comment
  (solve1 (parse-cmds "resources/day21-input.txt") "abcdefgh")
  ;; correct answer dbfgaehc
  (solve2 (parse-cmds "resources/day21-input.txt") "abcdefgh" "fbgdceah")
  ;; correct answer aghfcdeb
  )

(defn -main []
  (let [cmds (parse-cmds "resources/day21-input.txt")]
    (println "Part 1:" (solve1 cmds "abcdefgh"))
    (println "Part 2:" (solve2 cmds "abcdefgh" "fbgdceah"))))
