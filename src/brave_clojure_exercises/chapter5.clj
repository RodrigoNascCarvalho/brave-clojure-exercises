(ns brave-clojure-exercises.chapter5
  (:gen-class))

;; Exercise 1
(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(defn attr
  [attribute-name]
  (fn [character]
    (attribute-name (:attributes character))))

((attr :intelligence) character)

;; Exercise 2
(defn n-comp
        "Composes any number of functions passed (comp)"
        [& funcs]
        (fn [& args]
          (let [function-list (reverse funcs)]
               (reduce
                (fn [previous-fn-value comp-func]
                  (comp-func previous-fn-value))
                (apply (first function-list) args)
                (rest function-list)))))

((n-comp :intelligence :attributes) character)

(defn n-comp-2
  ([f] f)
  ([f & funcs]
   (fn [& args]
     (f (apply (apply n-comp-2 funcs) args)))))

((n-comp-2 :intelligence :attributes) character)

;;Exercise 3
(assoc-in {} [:cookie :monster :vocals] "Finntroll")
; => {:cookie {:monster {:vocals "Finntroll"}}}

(defn my-assoc-in
  [m [k & ks] v]
  (if (empty? ks)
    (assoc m k v)
    (assoc m k (my-assoc-in {} ks v))))

(my-assoc-in {:cookie 1} [:cookie :monster :vocal] "Finntroll")

;;Exercise 4
(def vehicle {:automobile { :wheels 2 :doors 0 :seats {:passenger "John"}}})

(update-in vehicle [:automobile :wheels] inc)
(update-in vehicle [:automobile :wheels] - 2)
(update-in vehicle [:automobile :doors] + 4)
(update-in vehicle [:automobile :seats :passenger] str " Wayne")

;;Exercise 5
(defn my-update-in
  [m ks f & args]
  (assoc-in m ks (apply f (get-in m ks) args)))

(my-update-in vehicle [:automobile :wheels] inc)
(my-update-in vehicle [:automobile :wheels] - 2)
(my-update-in vehicle [:automobile :doors] + 4)
(my-update-in vehicle [:automobile :seats :passenger] str " Wayne")
