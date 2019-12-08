(require '[clojure.string :as str])

(def image-data (str/split (slurp "input") #""))

(let [layers (partition (* 25 6) image-data)
      freqs (vec (map #(frequencies %) layers))
      min-zeros (apply min-key #(get % "0") freqs)
      image  (replace {"0" " ", "1" "#"} (reduce #(for [i (range (* 25 6))] (if (= (nth %2 i) (str 2)) (nth %1 i) (nth %2 i))) (reverse layers)))]
  (print (str/join "\n" (map str/join (partition 25 image)))
  (println (* (get min-zeros "2") (get min-zeros "1")))))
