(ns hazard.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hazard.common :as hazard]))

(def 
  ^{:private true}
  default-words (io/file (io/resource "mwords/113809of.fic")))

(defn- do-words
  ([words n]
   (hazard/random-strings-in-bounds words n))
  ([words n {:keys [min max] :or {min 0}}]
   (apply hazard/random-strings-in-bounds
          words
          n
          (filter #(not (nil? %)) [min max]))))

(defn- do-load-and-parse
  [file]
  (let [contents (slurp file)]
    (str/split-lines contents)))

(def 
  ^{:private true}
  load-and-parse (memoize do-load-and-parse))

(defmulti words
  (fn [x & args] (class x)))
(defmethod words String [path & args]
  (apply do-words (load-and-parse (io/file path)) args))
(defmethod words Number [& args]
  (apply do-words (load-and-parse default-words) args))
(defmethod words :default [& args]
  (apply do-words args))
