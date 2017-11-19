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

(def filename "ch2-suspects.csv")
(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name          identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def vamp-db (mapify (parse (slurp filename))))

(defn filter-vamps
  [vamp-db]
  (glitter-filter 3 vamp-db))

(defn names-only [vam-db]
  (map #(get % :name) (filter-vamps vamp-db)))

(defn append
  [db record]
  (conj db record))

(defn to-csv-row
  [{:keys [name glitter-index]}]
  (clojure.string/join "," [name glitter-index]))


(defn valid?
  [keys rec]
  (reduce #(and %1 (not (nil? (rec %2))))
          true
          keys))


(defn to-csv
  [db]
  (clojure.string/join "\n"
                       (map (to-csv-row vamp-keys) db)))

(defn to-csv-row
  [keys]
  (fn [rec]
    (clojure.string/join ","
                         (map #(rec %) keys))))

(defn go []
  (let [rec {:name "Jano" :glitter-index 1234}]
    (if (not (valid? vamp-keys rec))
      "Invalid record"
      (to-csv (append vamp-db rec)))))
