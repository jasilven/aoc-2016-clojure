(ns day25)

(defn solve []
  (- (Integer/valueOf "101010101010" 2) (* 4 633)))

(defn -main []
  (println "Part 1:" (solve)))
