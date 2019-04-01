(ns day4
  (:require [clojure.string :as str]))

(defn fname->rooms [fname]
  (->> (for [line (str/split-lines (str/trim (slurp fname)))]
         (->> (re-find #"^(\D*)(\d+)\[(\w*)\]" line)
              (drop 1)))
       (map #(hash-map :name (subs (first %) 0 (dec (count (first %))))
                       :sectorID (Integer/parseInt (second %))
                       :checksum (last %)))))

(defn letter-freqs
  "returns letters of word (as a string) in order of frequency"
  [word]
  (->> (frequencies word)
       (sort-by (juxt #(- (val %)) key))
       (map key)
       (apply str)))

(defn real-room? [room]
  (= (:checksum room)
     (subs (letter-freqs (str/replace (:name room) "-" "")) 0 5)))

(defn solve1 [fname]
  (->> (filter real-room? (fname->rooms fname))
       (map :sectorID)
       (reduce +)))

(defonce letters-az (into [] (map char (range 97 123))))

(defn shift-cipher
  "shift cipher ch by n. '-' is constantly ciphered to ' '."
  [n ch]
  (if (= ch \-)
    \ 
    (get letters-az (mod (+ n (- (int ch) 97))
                         (count letters-az)))))

(defn solve2 [fname]
  (->> (for [room (fname->rooms fname)
             :let [n (:sectorID room)]]
         (->> (map #(shift-cipher n %) (:name room))
              (apply str)
              (hash-map n)))
       (filter #(= (val (first %)) "northpole object storage"))
       ffirst
       first))

(comment
  (solve1 "resources/day4-input.txt")
  ;; correct answer 409147
  (solve2 "resources/day4-input.txt")
  ;; correct answer 991
  )

(defn -main []
  (println "part 1:" (solve1 "resources/day4-input.txt"))
  (println "part 2:" (solve2 "resources/day4-input.txt")))
