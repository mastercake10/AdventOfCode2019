(ns intcomputer)

(require '[clojure.string :as str])

(defn digits [num]
  (str/split (str num) #""))

(defn addzeros [in]
  (concat (repeat (- 5 (count in)) "0") in))

(defn getparam [modes at i valpos opcode relativebase]
  (cond 
    (= (nth modes at) 1)
      (nth opcode (+ i valpos) 0)
    (= (nth modes at) 0)
      (nth opcode (nth opcode (+ i valpos) 0) 0)
    (= (nth modes at) 2)
      (nth opcode (+ relativebase (nth opcode (+ i valpos) 0)) 0)))

(defn compute [input intcode advanced step pointer]
  (loop [list intcode i pointer out [] inputpointer 0 relativebase 0]
    ;(println relativebase)
    ;(println (map read-string (addzeros (digits (nth list i)))))
    ;(println (- (count (digits (nth list i))) 2))
    (let [opcode (nth list i)]
      (if (= opcode 99)
        (last out)
        (let [digs (map read-string (addzeros (digits opcode)))
              code (last digs)
              paramlength (- (count (digits opcode)) 2)
              val1 (getparam digs 2 1 i list relativebase)
              val2 (getparam digs 1 2 i list relativebase)
              res (if (= paramlength 2) (nth list (+ i 3) 0) (+ relativebase (nth list (+ 3 i) 0)))]
         ; (println res)
          (cond
            (= code 2) (recur (assoc list res (* val1 val2)) (+ i 4) out inputpointer relativebase)

            (and (= code 3) advanced) (recur (assoc list (+ relativebase (nth digs (+ 1 i) 0)) (nth input inputpointer)) (+ i 2) out (inc inputpointer) relativebase)
            (and (= code 5) advanced) (recur list (if (not (= val1 0)) val2 (+ i 3)) out inputpointer relativebase)
            (and (= code 6) advanced) (recur list (if (= val1 0) val2 (+ 3 i)) out inputpointer relativebase)
            (and (= code 7) advanced) (recur (assoc list res (if (< val1 val2) 1 0)) (+ 4 i) out inputpointer relativebase)
            (and (= code 8) advanced) (recur (assoc list res (if (= val1 val2) 1 0)) (+ 4 i) out inputpointer relativebase)
            (and (= code 9) advanced) (recur list (+ 2 i) out inputpointer (+ relativebase val1)) ; stimmt!!!

            ;(= code 3) (recur (assoc list (nth list (inc i)) (nth input inputpointer)) (+ i 2) out (inc inputpointer) relativebase)
            (= code 1) (recur (assoc list res (+ val1 val2)) (+ i 4) out inputpointer relativebase)
            (= code 4) (if step {:output val1 :intcode list :pointer (+ i 2)} (recur list (+ i 2) (conj out val1) inputpointer relativebase))))))))


(let [intcode (into '[] (map read-string (str/split (slurp "input") #",")))]
  [(compute [1] intcode false false 0)
  (compute [5] intcode true false 0)])



