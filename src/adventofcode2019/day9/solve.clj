(require '[intcomputer] '[clojure.string :as str])


(def intcode (into '[] (map read-string (str/split (slurp "input") #","))))

[(intcomputer/compute [1] (vec (concat intcode (take 400000 (repeat 0)))) true false 0)
 (intcomputer/compute [2] (vec (concat intcode (take 400000 (repeat 0)))) true false 0)]


