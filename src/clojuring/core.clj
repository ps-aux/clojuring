(ns clojuring.core
  (:gen-class))
(require 'clojuring.chapter-1)


(defn foo []
  (clojuring.chapter-1/go))


(defn -main
  [& args]
  (let [res (foo)]
    (println res)))
