(ns hazard.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [hazard.core-test]
              [hazard.common-test]))

(doo-tests 'hazard.core-test
           'hazard.common-test)
