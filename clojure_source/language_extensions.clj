(ns clojure-source.language-extensions
  (:use [proteus :only (let-mutable)]))


(defmacro def-all
  [& definitions]
  (let [code-wrap (for [x (partition 2 definitions)] `(def ~@x))]
    `(do ~@code-wrap)))

(defmacro +=
  [mutable fn-or-val]
  (if (fn? (eval fn-or-val))
    `(set! ~mutable (~fn-or-val ~mutable))
    `(set! ~mutable (+ ~mutable ~fn-or-val))))

(defmacro conj-all!
  [this-structure & args]
  (let [code-wrap (for [x args] `(conj! ~x))]
    `(doto ~this-structure
       ~@code-wrap)))


(defmacro ++
  [mutable]
  `(+= ~mutable 1))

(defmacro create-mutable-arguements
  [vector-of-args]
  `(for [[var# val#] (partition 2 '~vector-of-args)]
     (if (or (map? val#)
             (vector? val#)
             (set? val#))
       [var# `(transient ~val#)]
       [var# val#])))

(defmacro create-arguement-vector
  [args]
  `(loop [[[a# b#] :as code#] (create-mutable-arguements ~args)
          fixed-vector# []]
     (if (seq code#)
       (recur (rest code#) (conj fixed-vector# a# b#))
       fixed-vector#)))


(defn create-transient-list
  [vector-args]
  (remove #(= nil %)
          (for [[a b] vector-args]
            (if (seq? b)
              a))))

(defmacro loop-m
  [{:keys [variables base-case looping-body return-body]}]
  (eval ``(let-mutable ~(create-arguement-vector ~variables)
                       (while ~'~base-case
                                ~'~looping-body)
                       ~'~return-body)))



(defmacro loop
   [& args]
   `(loop-m ~(apply hash-map args)))
