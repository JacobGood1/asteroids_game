(ns clojure-source.main
  (:import (java_source Game AppConfig GLOBALS)
           (com.badlogic.gdx.backends.lwjgl LwjglApplication)
           (com.badlogic.gdx.graphics OrthographicCamera))
  (:require [clojure-source.input])
  (:use [clojure-source.open-gl-macros :only (gen-gl-fn-for-namespace)]))
(gen-gl-fn-for-namespace)


(defn render
  []
  (gl-clear-color :red 0
                  :green 0
                  :blue 0
                  :alpha 0)
  (gl-clear :primitive (GL_COLOR_BUFFER_BIT)))


(def camera (OrthographicCamera.))

(def game (Game. render clojure-source.input/input-processor))





(LwjglApplication. game (AppConfig.))


