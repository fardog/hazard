(ns hazard.common-test
  (:require #?(:clj [clojure.test :refer :all]
               :cljs [cljs.test :refer-macros [deftest is testing]])
            [hazard.common :refer [take-random-n random-strings-in-bounds]]))

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
    (let [taken (take-random-n breads 3 #(= 0 (.indexOf ^String % "b")))]
      (is (every? #(= 0 (.indexOf ^String % "b")) taken))
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
