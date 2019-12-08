(require '[clojure.string :as str])

(defn adjacent_1? [vec]
  (some #(> % 1) (map count (partition-by identity vec))))

(defn adjacent_2? [vec]
  (.contains (map count (partition-by identity vec)) 2))

(defn ascending? [vec]
  (every? false? (map < vec (concat [0] vec))))

(defn digits [num]
  (map read-string (str/split (str num) #"")))

[(count (filter #(and (adjacent_1? (digits %)) (ascending? (digits %))) (range 234208 765869)))
 (count (filter #(and (adjacent_2? (digits %)) (ascending? (digits %))) (range 234208 765869)))]
