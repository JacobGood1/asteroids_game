(ns clojure-source.debug
  (:import (java_source GLOBALS))
  (:require [clojure.repl])
  (:use [clojure.core.match :only (match)]))


 ;set to true if you want to alter things at runtime

(defn reload
  []
  (eval '(.reLoad clojure-source.main/game
                  clojure-source.main/render
                  clojure-source.main/camera
                  clojure-source.input/input-processor)))

(defn launch-debug-mode
  []
  (eval `(do (add-watch #'clojure-source.main/render
                        :render-key
                        (fn [key# ref# os# ns#]
                            (reload)))
             (add-watch #'clojure-source.input/input-processor
                        :input-processor-key
                        (fn [key# reference# old-state# new-state#]
                            (reload))))))



