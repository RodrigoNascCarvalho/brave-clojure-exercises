(ns brave-clojure-exercises.chapter3
  (:gen-class))

;; Ex 1 from Chapter 3
(str "This concats" " a " "string " ":D!")

(vector "String" (+ 1 2) [1 2] {:a 3})

(list "String" (+ 1 2) [1 2] {:a 3})
'("String" (+1 2) [1 2] {:a 3})

(hash-map :a 1 :a 2 :b "String" :c :D)

(hash-set 1 1 2 "String" "String" :a :a :A [1 2] [1 2])

;; Ex 2 from Chapter 3
(defn add100
  "Add 100 to a given number"
  [num]
  (+ num 100))

;;Ex 3 from Chapter 3
(defn dec-maker
  "Create custom decrementer"
  [num]
  #(- % num))

;;Ex 4 from Chapter 3
(defn mapset
  "Works just like map but returns a set"
  [func array]
  (set (map func array)))

;; Ex 5-6 from Chapter 3
(def asym-alien-body-parts [{:name "head" :size 3}
                             {:name "1-eye" :size 1}
                             {:name "1-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "1-shoulder" :size 3}
                             {:name "1-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "1-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "1-kidney" :size 1}
                             {:name "1-hand" :size 2}
                             {:name "1-knee" :size 2}
                             {:name "1-thigh" :size 4}
                             {:name "1-tower-leg" :size 3}
                             {:name "1-achilles" :size 1}
                             {:name "1-foot" :size 2}])


(defn generate-alien-part
  "Returns a numbered limb for an alien"
  [part number]
  {:name (clojure.string/replace (:name part) #"^1-" (str number "-"))
   :size (:size part)})

(defn matching-alien-part
  "Takes a part map, checks if it's the 1-member, and generates 4 more members."
  [part number-of-limbs]
  (if (nil? (re-find #"^1-" (:name part)))
    part
    (loop [num 2
         new-parts []]
      (if (> num number-of-limbs)
        new-parts
        (recur (inc num) (conj new-parts (generate-alien-part part num)))))))

(defn symmetrize-alien-body-parts
  "Expects a seq of alien body maps that have a :name and :size"
  [asym-body-parts number-of-limbs]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-alien-part part number-of-limbs)])))
          []
          asym-body-parts))
;; Ex 5-6 from Chapter 3

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
