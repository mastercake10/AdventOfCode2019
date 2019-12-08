(ns intcomputer)

(require '[clojure.string :as str])

(defn digits [num]
  (str/split (str num) #""))

(defn addzeros [in]
  (concat (repeat (- 4 (count in)) "0") in))

(defn compute [input intcode advanced step pointer]
  (loop [list intcode i pointer out [] inputpointer 0]
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
            (= code 2) (recur (assoc list res (* val1 val2)) (+ i 4) out inputpointer)

            (and (= code 5) advanced) (recur list (if (not (= val1 0)) val2 (+ i 3)) out inputpointer)
            (and (= code 6) advanced) (recur list (if (= val1 0) val2 (+ 3 i)) out inputpointer)
            (and (= code 7) advanced) (recur (assoc list res (if (< val1 val2) 1 0)) (+ 4 i) out inputpointer)
            (and (= code 8) advanced) (recur (assoc list res (if (= val1 val2) 1 0)) (+ 4 i) out inputpointer)

            (= code 3) (recur (assoc list (nth list (inc i)) (nth input inputpointer)) (+ i 2) out (inc inputpointer))
            (= code 1) (recur (assoc list res (+ val1 val2)) (+ i 4) out inputpointer)
            (= code 4) (if step {:output val1 :intcode list :pointer (+ i 2)} (recur list (+ i 2) (conj out val1) inputpointer))))))))


(let [intcode (into '[] (map read-string (str/split (slurp "input") #",")))]
  [(compute [1] intcode false false 0)
  (compute [5] intcode true false 0)])



