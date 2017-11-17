(ns clojuring.chapter-1)

(def humanoid-parts [{:name "head" :size 3}
                     {:name "left-eye" :size 1}
                     {:name "left-ear" :size 1}
                     {:name "mouth" :size 1}
                     {:name "nose" :size 1}
                     {:name "neck" :size 2}
                     {:name "left-shoulder" :size 3}
                     {:name "left-upper-arm" :size 3}
                     {:name "chest" :size 10}
                     {:name "back" :size 10}
                     {:name "left-forearm" :size 3}
                     {:name "abdomen" :size 6}
                     {:name "left-kidney" :size 1}
                     {:name "left-hand" :size 2}
                     {:name "left-knee" :size 2}
                     {:name "left-thigh" :size 4}
                     {:name "left-lower-leg" :size 3}
                     {:name "left-achilles" :size 1}
                     {:name "left-foot" :size 2}])


(defn matching-part [{:keys [name size]}]
  {:name (clojure.string/replace name #"^left-" "right-")
   :size size})

(defn create-hobbit
  [parts]
  (reduce (fn [acc p]
            (into acc
                  (set [p (matching-part p)])))
          []
          parts))

(defn hobbit-size
  [hbt]
  (reduce #(+ %1 (:size %2)) 0 hbt))

(defn hit
  [hbt]
  (let [size (hobbit-size hbt)
        hit-val (rand size)]
    (loop [[x & xs] hbt
           acc 0]
      (if (< hit-val acc)
        x
        (recur xs (+ acc (:size x)))))))


(defn dec-maker
  [by]
  #(- % by))

(def dec-10 (dec-maker 10))

(defn mapset
  [f xs]
  (set (map f xs)))

(def lst [1 2 3 4])

(def creature-parts [
                  {:name "head" :single true}
                  {:name "torso" :single true}
                  {:name "hand"}
                  {:name "leg"}])

(defn clone-part
  [n]
  (fn [{:keys [name single] :as p}]
    (if single
      [p]
      (map #(hash-map :name (str name "-" %)) (range n)))))

(defn create-creature
  [parts mult-by]
  (let [clone (clone-part mult-by)]
    (reduce (fn [acc p]
              (println (clone p))
              (into acc
                    (clone p)))
            []
            parts)))

(defn go []
  (create-creature creature-parts 3))
