(require '[clojure.string :as str])

(defn normalize [angle]
  (if (< angle 0) (+ angle (* 2 Math/PI)) angle))

(let [input (map #(str/split % #"") (str/split-lines (slurp "input")))
      asts (filter identity (for [y (range (count input)) x (range (count (nth input 0)))] (if (= (nth (nth input y) x) "#") [x y])))
      station (apply max-key last (for [ast asts] [[(first ast) (last ast)] (count (distinct (map #(Math/atan2 (- (last ast) (last %)) (- (first ast) (first %))) asts)))]))
      astbydist (map #(sort-by last %) (sort-by first (vals (group-by first (map (fn [x] [(normalize (- (Math/atan2 (- (last (first station)) (last x)) (- (first (first station)) (first x))) (/ Math/PI 2))) x (+ (Math/abs (- (first (first station)) (first x))) (Math/abs (- (last (first station)) (last x))))]) asts)))))]

  [(last station)
   (let [result (loop [i 0 depth 0 res [] th 0]
                  (cond
                    (> th 199) res
                    (and (< i (count astbydist)) (< depth (count (nth astbydist i))))
                    (recur (inc i) 0 (nth (nth astbydist i) depth) (inc th))
                    (>= i (count astbydist)) (recur 0 (inc depth) res th)
                    (>= depth (count (nth astbydist i))) (recur (inc i) depth res th)))]
     (+ (* (first (second result)) 100) (last (second result))))])