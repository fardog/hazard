(ns hazard.common)

(defn- do-take-random-n
  "Given a collection, take n random items."
  [coll n]
  (let [candidate-count (count coll)]
    (if (<= n candidate-count)
      (with-meta (take n (shuffle coll)) {:candidate-count candidate-count})
      (throw #?(:clj (Exception. "More items requested than are available.")
                :cljs (js/Error. "More items requested than are available."))))))

(defn take-random-n
  "Given a collection of items, retrieve n items, optionally those that pass
  filter f"
  ([coll n]
   (do-take-random-n coll n))
  ([coll n f]
   (do-take-random-n (filter f coll) n)))

(defn random-strings-in-bounds
  "Given a collection of strings, get n random strings of length
  low <= len <= high"
  ([strings n]
   (take-random-n strings n))
  ([strings n low]
   (take-random-n strings n #(>= (count %) low)))
  ([strings n low high]
   (take-random-n strings n #(and (>= (count %) low) (<= (count %) high)))))

(defn -do-words
  ([words n]
   (random-strings-in-bounds words n))
  ([words n {:keys [min max] :or {min 0}}]
   (apply random-strings-in-bounds
          words
          n
          (filter #(not (nil? %)) [min max]))))
