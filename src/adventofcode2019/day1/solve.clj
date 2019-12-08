(require '[clojure.string :as str])

(let [list (map read-string (str/split-lines (slurp "input")))
      calc-fuel #(- (int (/ % 3)) 2)]
  [(reduce + (map calc-fuel list))
   (reduce + (mapcat (fn [x] (rest (take-while #(> % 0) (iterate calc-fuel x)))) list))]
)
