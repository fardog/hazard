(ns hazard.core-test
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.test :refer :all]
            [hazard.core :refer :all]))

(def five  ^{:private true} ["anpan" "arepa" "bagel" "bammy" "bialy"])
(def six   ^{:private true} ["damper" "injera"])
(def seven ^{:private true} ["bannock" "biscuit" "brioche" "challah"])
(def eight ^{:private true} ["baguette" "focaccia"])
(def breads (concat five six seven eight))

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

(deftest options
  (testing "Can ask for minimum length"
    (is (= (sort (words breads (count eight) {:min 8}))
           (sort eight))))
  (testing "Can ask for maximum length"
    (is (= (sort (words breads (count five) {:max 5}))
           (sort five))))
  (testing "Can ask for min and max length"
    (let [expected (concat six seven)]
      (is (= (sort (words breads (count expected) {:min 6 :max 7}))
             (sort expected))))))

(deftest failures
  (testing "Throws when asked for more words than are in available"
    (is (thrown-with-msg? Exception #"available"
                          (words breads (inc (count breads))))))
  (testing "Throws when asked for more words than in criteria"
    (is (thrown-with-msg? Exception #"available"
                          (words breads
                                 (inc (count (concat six seven)))
                                 {:min 6 :max 7})))))
