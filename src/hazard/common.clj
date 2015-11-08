(ns hazard.common)

(defn- do-take-random-n
  "Given a collection, take n random items."
  [coll n]
  (if (<= n (count coll))
    (take n (shuffle coll))
    (throw (Exception. "More items requested than are available."))))

(defn take-random-n
  "Given a collection of items, retrieve n items, optionally those that pass
  filter f"
  ([coll n]
   (do-take-random-n coll n))
  ([coll n f]
   (do-take-random-n (filter f coll) n)))

(defn random-strings-in-bounds
  "Given a collection of strings, get n random strings of length
  low < len < high"
  ([strings n]
   (take-random-n strings n))
  ([strings n low]
   (take-random-n strings n #(>= (count %) low)))
  ([strings n low high]
   (take-random-n strings n #(and (>= (count %) low) (<= (count %) high)))))
