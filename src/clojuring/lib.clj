(ns clojuring.lib)

(defn my-reduce
  ([f arr]
   (let [[first & arr] arr]
     (my-reduce f first arr)))
  ([f first arr]
   (loop [[h & tail] arr
          acc first]
     (if (nil? h)
       acc
       (recur tail (f acc h))))))

