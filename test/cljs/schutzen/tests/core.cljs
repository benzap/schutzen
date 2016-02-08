(ns schutzen.tests.core
  (:require [cljs.test :refer-macros [run-all-tests]]
            [schutzen.tests.utils]
            [schutzen.tests.array2]
            ))



(defn ^:export run-schutzen-tests []
  (enable-console-print!)
  (run-all-tests #"schutzen\.tests.*"))
