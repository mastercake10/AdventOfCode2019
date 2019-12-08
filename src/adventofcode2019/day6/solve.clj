(require '[clojure.string :as str])

(defn getlowerorbit [rels, key]
  (if (contains? rels key)
    (conj (getlowerorbit rels (keyword (get rels key))) key)))

(let [input (str/split-lines (slurp "input"))
      relations (zipmap (map #(keyword (nth (str/split % #"\)") 1)) input) (map #(nth (str/split % #"\)") 0) input))]
  [(reduce + (for [entry relations] (count (getlowerorbit relations (key entry)))))
   (loop [trace1 {} trace2 {} orbit1 :YOU orbit2 :SAN]
     (let [lowest1 (keyword (get relations orbit1))
           lowest2 (keyword (get relations orbit2))
           newtrace1 (concat trace1 #{lowest1})
           newtrace2 (concat trace2 #{lowest2})]
       (if (or (some #{lowest2} trace1) (some #{lowest1} trace2))
          (if (some #{lowest1} trace2)
            (+ (.indexOf newtrace1 lowest1) (.indexOf newtrace2 lowest1))
            (+ (.indexOf newtrace1 lowest2) (.indexOf newtrace2 lowest2)))

         (recur newtrace1 newtrace2 lowest1 lowest2))))])