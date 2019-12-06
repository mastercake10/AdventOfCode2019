(require '[clojure.string :as str])

(defn digits [num]
  (str/split (str num) #""))

(defn addzeros [in]
  (concat (repeat (- 4 (count in)) "0") in))

(defn solve [input, advanced]
  (let [list (into '[] (map read-string (str/split (slurp "input") #",")))]
    (loop [list list i 0 out []]
      (let [opcode (nth list i)]
        (if (= opcode 99)
          (last out)
          (let [digs (map read-string (addzeros (digits opcode)))
                code (last digs)
                val1 (if (= (nth digs 1) 1)
                  (nth list (+ i 1) 0)
                  (nth list (nth list (+ i 1) 0) 0))
                val2 (if (= (nth digs 0) 1) 
                  (nth list (+ i 2) 0) 
                  (nth list (nth list (+ i 2) 0) 0))
                res (nth list (+ i 3) 0)]
            (cond
              (= code 2) (recur (assoc list res (* val1 val2)) (+ i 4) out)

              (and (= code 5) advanced) (recur list (if (not (= val1 0)) val2 (+ i 3)) out)
              (and (= code 6) advanced) (recur list (if (= val1 0) val2 (+ 3 i)) out)
              (and (= code 7) advanced) (recur (assoc list res (if (< val1 val2) 1 0)) (+ 4 i) out)
              (and (= code 8) advanced) (recur (assoc list res (if (= val1 val2) 1 0)) (+ 4 i) out)

              (= code 3) (recur (assoc list (nth list (inc i)) input) (+ i 2) out)
              (= code 1) (recur (assoc list res (+ val1 val2)) (+ i 4) out)
              (= code 4) (recur list (+ i 2) (conj out val1)))))))))


[(solve 1 false)
 (solve 5 true)]



