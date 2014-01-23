(ns clojure-source.input
  (:import (com.badlogic.gdx InputAdapter Input$Keys))
  (:require [clojure.reflect])
  (:use [clojure.core.match :only (match)]))

(def to-map (comp (partial apply hash-map) flatten))

(def input-keys
  (apply hash-map
         (flatten (for [{current-key :name} (:members (clojure.reflect/reflect Input$Keys))
                                            :when (not (re-find #"[a-z]+" (str current-key)))]
                       [(eval (symbol (str "Input$Keys/" current-key))) current-key]))))

(def game-keys-current (atom (to-map (for [[game-key-code _] input-keys] [game-key-code false]))))
(def game-keys-previous (atom @game-keys-current))

(defn down?
  [k]
  (@game-keys-current k))

(defn pressed?
  [k]
  (and (@game-keys-current k) (not (@game-keys-previous k))))

(defn update-keys!
  []
  (reset! game-keys-previous @game-keys-current))

(defn set-key!
  [k b]
  (swap! game-keys-current assoc k b))

;tomm make a macro to detect all keys in input without coding it all

(def input-processor
  (proxy [InputAdapter] []
    (keyDown [key]
      (match [(input-keys key)]
             ['W] (set-key! key true)
             ['A] (set-key! key true)
             ['S] (set-key! key true)
             ['D] (set-key! key true)
             :else (println " dat ayze"))
      true)
    (keyUp [key]
      (match [(input-keys key)]
             ['W] (set-key! key false)
             ['A] (set-key! key false)
             ['S] (set-key! key false)
             ['D] (set-key! key false)
             :else (println "no match"))
      true)))

