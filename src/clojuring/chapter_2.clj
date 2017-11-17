(ns clojuring.chapter-2)

(def f1 (constantly 4))

(def f2 #(str "--" %))

(def f3 identity)

(defn calc
  [xs]
  (map (fn [x]
         (map #(% x) [f1 f2 f3]))
       xs))

(defn inc-vals
  [map by]
  (reduce
    (fn [acc [k v]]
      (assoc acc k (+ v by)))
    {}
    map))

(defn item
  [i]
  (println "creating an item " i)
  {:name "an item"})

(def items (map item (range 100)))

(defn go []
  (println "going")
  (concat (take 33 items) (take 33 items)))
