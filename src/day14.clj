(ns day14
  (:import (java.security MessageDigest)))

(def samech3 (re-pattern "(\\w)\\1{2}"))
(def samech5 (re-pattern "(\\w)\\1{4}"))

(defn md5 [^String s]
  (->> (.getBytes s)
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)
       (format "%032x")))

(defn solve [salt index]
  (loop [n 0
         triples (into [] (repeat 1000 nil))
         keys []]
    (if (>= (count keys) 64)
      (nth keys 63)
      (let [h (nth (iterate md5 (str salt n)) index)
            ch5 (into #{} (map ffirst (re-seq samech5 h)))
            triple (filter #(ch5 (:key %)) triples)
            triples (conj (subvec triples 1)
                          {:n n :key (ffirst (re-find samech3 h))})]
        (recur (inc n)
               triples
               (if (and (seq ch5)
                        (seq triple))
                 (apply conj keys (map :n triple))
                 keys))))))

(comment
  (solve "yjdafjpo" 1)
  ;; correct answer 25427
  (solve "yjdafjpo" 2017)
  ;;correct answer is 22034 22045
  )

(defn -main []
  (println "part 1:" (solve "yjdafjpo" 1))
  (println "part 2:" (solve "yjdafjpo" 2017)))
