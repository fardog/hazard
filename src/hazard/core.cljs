(ns hazard.core
  (:require [clojure.string :as str]
            [hazard.common :as hazard]))

(defn words [& args]
  (apply hazard/-do-words args))
