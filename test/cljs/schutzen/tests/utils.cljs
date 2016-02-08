(ns schutzen.tests.utils
  (:require [cljs.test :refer-macros [deftest is testing]]
            [schutzen.utils :as utils]))

(deftest test-numbers
  (is (= 1 1)))

(deftest test-clamp-lower
  (is (utils/clamp-lower 4 5) 5)
  (is (utils/clamp-lower 4.1 4) 4.1))

(deftest test-clamp-upper
  (is (utils/clamp-upper 10 11) 10)
  (is (utils/clamp-upper 11 12) 12))

(deftest test-clamp
  (is (utils/clamp 10 8 12) 10)
  (is (utils/clamp 10 12 24) 12)
  (is (utils/clamp 26 12 24) 24))

