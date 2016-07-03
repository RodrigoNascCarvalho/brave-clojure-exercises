(ns brave-clojure-exercises.chapter4
  (:gen-class))

;; Reimplementing map as book suggested, only one array as arg for now
(defn map-with-reduce
  [fun array]
  (reduce (fn [new-map element]
            (into new-map (fun element)))
          []
          array))

;;(map-with-reduce #(println %) [1 2 3])

;; Exercise for FWPD below
(def filename "resources/suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  "Converts str to int"
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  "Converts a value based on its vampkey"
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Parse CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Returns a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  "Filters vampires by passing a minimum glitter value"
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

;;Ex1
(defn glitter-filter-names
  "Filters vampires by passing a minimum glitter value, returns only names"
  [minimum-glitter records]
  (map #(:name %)
       (filter #(>= (:glitter-index %) minimum-glitter) records)))
;;

;;Ex2
(defn append
  "Appends a new possible vampire to the list of suspects"
  [records record]
  (conj records record))
;;

;;Ex3
(def validations {:name #(string? %)
                  :glitter-index #(integer? %)})

(defn validate
  "Validate a vampire record"
  [validations record]
  (def validated-record (reduce (fn [final-record [vamp-key vamp-value]]
                                   (if ((vamp-key validations) vamp-value)
                                     (assoc final-record vamp-key vamp-value)
                                     (into final-record nil)))
                                 {}
                                 record))
  (if (= (count validated-record) 2) validated-record))

(defn new-append
  "Append only validated value"
  [records record]
  (def validated (validate validations record))
  (if (nil? validated)
    (println "Invalid record!")
    (conj records (validate validations record))))
;; Ends here

;;Ex4
(defn map-to-csv
  "Converts current list of maps back to CSV"
  [list]
  (clojure.string/join "\n"
       (map #(str (:name %) "," (:glitter-index %)) list)))
;;
