(require '[clojure.java.io :as io] '[clojure.string :as str])

(let [list (map read-string (str/split-lines (slurp "input")))
      calc-fuel #(- (int (/ % 3)) 2)]
  (println 
    (str "part1: " (reduce + (map calc-fuel list)))
      "part2: " (reduce + (map 
        #(loop [fuel % total 0]
          (let [newfuel (calc-fuel fuel)]
            (if (> newfuel 0)
              (recur newfuel (+ total newfuel))
              total)))
        list))))

