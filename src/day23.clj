(ns day23
  (:require [clojure.string :as str]))

(defn sint? [s] (boolean (re-find #"\-?\d+" s)))

(defn exe-cpy [[_ arg reg] {:keys [regs] :as prog}]
  (-> (if (sint? reg)
        prog
        (assoc-in prog [:regs (keyword reg)]
                  (if (sint? arg) (Integer/parseInt arg)
                      (get regs (keyword arg)))))
      (update-in [:pointer] inc)))

(defn exe-incdec [[_ arg] {:keys [regs] :as prog} fn]
  (-> (if (sint? arg)
        prog
        (update-in prog [:regs (keyword arg)] fn))
      (update-in [:pointer] inc)))

(defn exe-jnz [[_ arg1 arg2] {:keys [pointer regs] :as prog}]
  (let [jump (if (sint? arg2) (Integer/parseInt arg2)
                 (get regs (keyword arg2)))
        a1 (if (sint? arg1) (Integer/parseInt arg1)
               (get regs (keyword arg1)))]
    (if (zero? a1)
      (update-in prog [:pointer] inc)
      (assoc-in prog [:pointer] (+ pointer jump)))))

(defn toggle [instruction]
  (condp = (subs instruction 0 3)
    "inc" (str/replace-first instruction "inc" "dec")
    "dec" (str/replace-first instruction "dec" "inc")
    "tgl" (str/replace-first instruction "tgl" "inc")
    "jnz" (str/replace-first instruction "jnz" "cpy")
    "cpy" (str/replace-first instruction "cpy" "jnz")
    :else
    (throw (Exception. (str "Cannot toggle instruction:" instruction)))))

(defn exe-tgl [[_ arg] {:keys [regs instructions pointer] :as prog}]
  (let [a1 (if (sint? arg) (Integer/parseInt arg)
               (get regs (keyword arg)))
        jump (+ pointer a1)]
    (-> (if (and (< jump (count instructions))
                 (>= jump 0))
          (assoc-in prog [:instructions]
                    (into [] (concat (subvec instructions 0 jump)
                                     (vector (toggle (get instructions jump)))
                                     (subvec instructions (inc jump)))))
          prog)
        (update-in [:pointer] inc))))

(defn execute [{:keys [regs pointer instructions] :as program}]
  (let [instruction (vec (re-seq #"\S+" (get instructions pointer)))]
    (condp = (first instruction)
      "cpy" (exe-cpy instruction program)
      "inc" (exe-incdec instruction program inc)
      "dec" (exe-incdec instruction program dec)
      "jnz" (exe-jnz instruction program)
      "tgl" (exe-tgl instruction program)
      :else (throw (Exception. (str "Unknown instruction:" instruction))))))

(defn solve1 [fname regs]
  (let [instructions (str/split-lines (str/trim (slurp fname)))
        icount (count instructions)]
    (loop [{:keys [regs pointer instructions] :as program}
           {:regs regs :pointer 0 :instructions instructions}]
      (if (or (neg? pointer)
              (>= pointer icount))
        (:a regs)
        (recur (execute program))))))

(defn factorial [n]
  (reduce * (range 1 (inc n))))

(defn solve2 [fname n]
  (+ (factorial 12)
     (- (solve1 fname {:a 7 :b 0 :c 0 :d 0})
        (factorial 7))))

(comment
  (solve1 "resources/day23-input.txt" {:a 7 :b 0 :c 0 :d 0})
  ;; correct answer 12663
  (solve2 "resources/day23-input.txt" 12)
  ;; correct answer 479009223
  )

(defn -main []
  (println "Part 1:" (solve1 "resources/day23-input.txt" {:a 7 :b 0 :c 0 :d 0}))
  (println "Part 2:" (solve2 "resources/day23-input.txt" 12)))
