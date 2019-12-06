(require '[clojure.string :as str])

(defn move [frompos dir steps]
  (for [x (range steps)]
    (if (= (nth dir 0) 0)
      [(nth frompos 0) (+ (nth frompos 1) (* x (nth dir 1)))]
      [(+ (nth frompos 0) (* x (nth dir 0))) (nth frompos 1)])))

(defn trace [wire]
  (let [dirs {:R [1 0] :L [-1 0] :U [0 1] :D [0 -1]}]
    (loop [pos [0 0] i 0 result []]
      (if (> i (- (count wire) 1))
        result
        (let [dir (get dirs (keyword (first (str/split (nth wire i) #""))))
              steps (read-string (str/join (rest (str/split (nth wire i) #""))))]
          (recur
           (map + (for [d dir] (* d steps)) pos)
           (inc i)
           (concat result (move pos dir steps))))))))


(let [wires (map #(str/split % #",") (str/split-lines (slurp "input")))
      p1 (rest (trace (nth wires 0)))
      p2 (rest (trace (nth wires 1)))
      is (clojure.set/intersection (set p1) (set p2))]
  [(apply min (map #(+ (Math/abs (last %)) (Math/abs (first %))) is))
   (+ (apply min (map #(+ (.indexOf p1 %) (.indexOf p2 %)) is)) 2)])
