(ns schutzen.tests.array2
  (:require [cljs.test :refer-macros [deftest is testing]]
            [schutzen.array2 
             :refer [aa ac ax ax=
                     ax+ ax- ax*
                     ay ay= ay+ ay-
                     ay* a+ a++ a- a--
                     a** a== a**i a->v
                     v->a]]))

(deftest test-creation
  (is (array? (aa 0 0)))

  (let [x (aa 1 1)]
    (is (not= (ac x) x))))

(deftest test-components
  (let [x (aa 5 4)]
    (is (= (ax x) 5))
    (is (= (ay x) 4))
    ))

(deftest test-assignment
  (let [test-array (aa 5 5)]
    (is (= (ax test-array) 5))
    (ax= test-array 6)
    (is (= (ax test-array) 6))
    )
  
  (let [test-array (aa 5 5)]
    (is (= (ay test-array) 5))
    (ay= test-array 6)
    (is (= (ay test-array) 6))
    ))

(deftest test-addition
  (let [test-array (aa 5 5)]
    (ax+ test-array 1)
    (is (= (ax test-array) 6))
    ))
