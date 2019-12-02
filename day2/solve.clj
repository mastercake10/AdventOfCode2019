(require '[clojure.string :as str])


(defn dostep [[codes, i]]
  [(assoc codes (nth codes (+ 3 i))
          ((if (= 1 (nth codes i)) + *) (nth codes (nth codes (+ 1 i))) (nth codes (nth codes (+ 2 i)))))
   (+ i 4)])

(defn getnum [list]
  (first (first (last
     (take (/ (count list) 4) (iterate dostep [list 0]))))))

(defn solve [list noun verb]
  (getnum (assoc (assoc list 1 noun) 2 verb)))

(let [list (into '[] (map read-string (str/split (slurp "input") #",")))]
  [(solve list 12 2)
   (first (for [i (range 99) j (range 99) :when (= (solve list i j) 19690720)] (+ (* i 100) j)))])
