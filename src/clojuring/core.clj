(ns clojuring.core
  (:gen-class))
(require 'clojuring.chapter-2)


(defn foo []
  (clojuring.chapter-2/go))


(defn -main
  [& args]
  (let [res (foo)]
    (println res)))
