(ns clojure-source.open-gl-macros
  (:import (com.badlogic.gdx.graphics GL10)
           (com.badlogic.gdx Gdx))
  (:require [clojure.reflect]))



(comment
  --libgdx--
  com.badlogic.gdx.Gdx
  GL10 GL11 GL20

  --lwjgl--
  org.lwjgl.opengl
  GL10 GL12 GL13 GL14 GL15
  GL20 GL21
  GL30 GL31 GL32 GL33
  GL40 GL41 GL42 GL43)

;define gl primitvies and gl functions!



(def gl-data '[com.badlogic.gdx.graphics.GL10 com.badlogic.gdx.Gdx]) ;version library

(def gl-version (symbol (str (gl-data 0) "/")))
(def gl-library (symbol (str (gl-data 1) "/" "gl")))



(def gl-functions '[.glClearColor .glClear]) ;add new ones as you come across them!
(def gl-primitives (for [{method :name} (:members (clojure.reflect/reflect com.badlogic.gdx.graphics.GL10))
                         :when (not (re-find #"[a-z]+" (str method)))]
                     method))



(defn make-gl-function-name
  [gl-fn]
  (symbol (apply str
                 (for [x (seq (rest (str gl-fn)))]
                   (if (Character/isUpperCase x)
                     (str "-" (Character/toLowerCase x))
                     x)))))

(defmacro spray-code-functions
  [fn & args]
  (let [key-vals (remove #(keyword? %) args)]
       `(~fn ~gl-library ~@key-vals)))


(defmacro spray-code-primitives
  [primitive]
  (eval `(symbol ~(str gl-version primitive))))

(defmacro make-gl-primitive ;works!
  [gl-primitive]
  `(defmacro ~gl-primitive
     []
     (spray-code-primitives ~gl-primitive)))


(defmacro make-gl-function ;works!
  [gl-function]
  `(defmacro ~(make-gl-function-name gl-function)
     [& args#]
     `(spray-code-functions ~'~gl-function ~@args#)))

(defn gen-gl-fn-for-namespace
  []
  (doseq [function gl-functions]
    (eval `(make-gl-function ~function)))
  (doseq [primitive gl-primitives]
    (eval `(make-gl-primitive ~primitive))))
