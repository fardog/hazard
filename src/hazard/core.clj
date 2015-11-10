(ns hazard.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [hazard.common :as hazard]))

(def 
  default-words (io/resource "mwords/113809of.fic"))

(defn- do-load-and-parse
  [file]
  (let [contents (slurp file)]
    (str/split-lines contents)))

(def 
  ^{:private true}
  load-and-parse (memoize do-load-and-parse))

(def words hazard/words)

(defmethod words String [path & args]
  (apply hazard/-do-words (load-and-parse (io/file path)) args))
(defmethod words Number [& args]
  (apply hazard/-do-words (load-and-parse default-words) args))
