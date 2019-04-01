(ns day16
  (:require [clojure.string :as str]))

(defn generate [data]
  (->> (str/reverse data)
       (mapv #(if (= \0 %) \1 \0))
       (apply str data \0)))

(defn checksum [data]
  (->> (partition 2 data)
       (mapv #(if (= (first %) (second %)) \1 \0))))

(defn substr [len s]
  (subs s 0 len))

(defn solve [data len]
  (->> (iterate generate data)
       (drop-while #(< (count %) len))
       first
       (substr len)
       (iterate checksum)
       (filter (comp odd? count))
       first
       (apply str)))

(comment
  (solve "10001110011110000" 272)
  ;; correct answer 10010101010011101
  (solve "10001110011110000" 35651584)
  ;; correct answer 01100111101101111
  )

(defn -main []
  (println "part 1:" (solve "10001110011110000" 272))
  (println "part 2:" (solve "10001110011110000" 35651584)))
