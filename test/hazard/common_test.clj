(ns hazard.common-test
  (:require [clojure.test :refer :all]
            [hazard.common :refer :all]))

(def five  ^{:private true} ["anpan" "arepa" "bagel" "bammy" "bialy"])
(def six   ^{:private true} ["damper" "injera"])
(def seven ^{:private true} ["bannock" "biscuit" "brioche" "challah"])
(def eight ^{:private true} ["baguette" "focaccia"])
(def breads (concat five six seven eight))

(deftest take-random-n-from-list
  (testing "Can take random items from a list"
    (let [taken (take-random-n breads 3)]
      (is (every? (fn [t] #(some (= % t) breads)) taken))))
  (testing "Can take random items from a list, given a filter"
    (let [taken (take-random-n breads 3 #(.startsWith ^String % "b"))]
      (is (every? #(.startsWith ^String % "b") taken))
      (is (every? (fn [t] #(some (= % t) breads)) taken)))))

(deftest random-strings-in-bounds-from-list
  (testing "Can take random strings from a list"
    (let [taken (random-strings-in-bounds breads 2)]
      (is (every? (fn [t] #(some (= % t) breads)) taken))))
  (testing "Can take random strings fewer than count chars"
    (is (= (sort (random-strings-in-bounds breads (count eight) 8))
           (sort eight))))
  (testing "Can take random strings in bounds"
    (let [expected (concat six seven)]
      (is (= (sort (random-strings-in-bounds breads (count expected) 6 7))
             (sort expected))))))

(deftest wordlist
  (testing "Can provide a list of words"
    (let [word-list ["beep" "boop"]
          one (first (words word-list 1))
          both (words word-list 2)]
      (is (some #(= % one) word-list))
      (is (= (sort both) (apply list word-list))))))

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
