(require '[intcomputer] '[clojure.string :as str])


(def intcode (into '[] (map read-string (str/split (slurp "input") #","))))

(defn permutations [colls]
  (if (= 1 (count colls))
    (list colls)
    (for [head colls
          tail (permutations (disj (set colls) head))]
      (cons head tail))))

(defn calc [input, phasesettings, pointer]
  (if (< pointer (count phasesettings))
    (calc (intcomputer/compute [(nth phasesettings pointer) input] intcode true false 0) phasesettings (inc pointer))
    input))


(defn calc2 [prephased]
  (loop [pointer 0 programs prephased]
    (if (< pointer (count programs))
      (let [oldstate (nth programs pointer)
            lastresult (nth programs (if (= pointer 0) (dec (count programs)) (dec pointer)))
            result (intcomputer/compute [(get lastresult :output)] (get oldstate :intcode) true true (get oldstate :pointer))]
        (if (contains? result :intcode)
         (recur (inc pointer) (assoc programs pointer result))
         (get lastresult :output)))
      (recur 0 programs))))

(defn prephase [phasesettings]
  (loop [programs [] i 0 last 0]
    (if (< i 5)
      (let [result (intcomputer/compute [(nth phasesettings i) last] intcode true true 0)]
        (recur (conj programs result) (inc i) (get result :output)))
      programs)))

;; part 1
[(apply max
  (map #(calc 0 % 0)
  (permutations [0 1 2 3 4])))

;; part 2
(apply max
  (map #(calc2 (prephase %))
  (permutations [9 8 7 6 5])))]
