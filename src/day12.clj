(ns day12
  (:require [clojure.string :as str]))

(defn exe-cpy [instruction {:keys [regs] :as program} ]
  (-> (if (contains? regs (keyword (second instruction)))
        (assoc-in program [:regs (keyword (last instruction))]
                  (get regs (keyword (second instruction))))
        (assoc-in program [:regs (keyword (last instruction))]
                  (Integer/parseInt (second instruction))))
      (update-in [:pointer] inc)))

(defn exe-fn [instruction {:keys [regs] :as program} fn]
  (-> (update-in program [:regs (keyword (second instruction))] fn)
      (update-in [:pointer] inc)))

(defn exe-jnz [instruction {:keys [regs pointer] :as program}]
  (let [arg (if (contains? regs (keyword (second instruction)))
              (get regs (keyword (second instruction)))
              (Integer/parseInt (second instruction)))]
    (if (zero? arg)
      (update-in program [:pointer] inc)
      (update-in program [:pointer] + (Integer/parseInt (last instruction))))))

(defn execute [{:keys [regs pointer instructions] :as program}]
  (let [instruction (re-seq #"\S+" (get instructions pointer))]
    (condp = (first instruction)
      "cpy" (exe-cpy instruction program)
      "inc" (exe-fn instruction program inc)
      "dec" (exe-fn instruction program dec)
      "jnz" (exe-jnz instruction program)
      :else (throw (Exception. (str "Unknown instruction:" instruction))))))

(defn solve [fname regs]
  (let [instructions (str/split-lines (str/trim (slurp fname)))
        icount (count instructions)]
    (loop [{:keys [regs pointer instructions] :as program}
           {:regs regs
            :pointer 0
            :instructions instructions}]
      (if (or (neg? pointer)
              (>= pointer icount))
        (:a regs)
        (recur (execute program))))))

(comment
  (solve "resources/day12-input.txt" {:a 0 :b 0 :c 0 :d 0})
  ;; correct answer 318009
  (solve "resources/day12-input.txt" {:a 0 :b 0 :c 1 :d 0})
  ;; correct answer 9227663
  )

(defn -main []
  (println "part 1:" (solve "resources/day12-input.txt" {:a 0 :b 0 :c 0 :d 0}))
  (println "part 2:" (solve "resources/day12-input.txt" {:a 0 :b 0 :c 1 :d 0})))
