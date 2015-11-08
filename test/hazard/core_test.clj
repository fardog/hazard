(ns hazard.core-test
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.test :refer :all]
            [hazard.core :refer :all]))

(def test-file-path "test/hazard/fixtures/words.txt")

(deftest default-wordlist
  (testing "Loads default wordlist"
    (is (= 5 (count (words 5))))))

(deftest wordlist
  (testing "Can provide a list of words"
    (let [word-list ["beep" "boop"]
          one (first (words word-list 1))
          both (words word-list 2)]
      (is (some #(= % one) word-list))
      (is (= (sort both) (apply list word-list))))))

(deftest wordfile
  (testing "Can provide a file containing words"
    (let [word-list (str/split-lines (slurp (io/file test-file-path)))
          taken (words test-file-path 2)]
      (is (every? (fn [t] #(some (= % t) word-list)) taken)))))
