(ns parse-au-results.core
  #_(:use [pl.danieljanus.tagsoup])
  (:require [clj-http.client :as client])
  (:gen-class))

(defn write-to-file [reg-no response]
  (spit (str reg-no ".html")
        (:body response)))

(defn fetch-reg-write! [reg-no]
  ;;  (println "fetching " reg-no " ...")
  (try
    (write-to-file reg-no
                   (client/get
                    (str "http://aucoe.annauniv.edu/cgi-bin/result/cgrade.pl?regno="
                         reg-no)
                    {:insecure? true}))
    (catch Exception e
      (do (println "Caught some exception.. re-trying for " reg-no)
          (flush)
          (fetch-reg-write! reg-no)))))

(defn start-threads [reg-start reg-end]
  (def fetched-map
    (map #(future (fetch-reg-write! %))
         (range reg-start reg-end))))

(defn print-stats []
  (let [c (atom -1)]
    (while (not (zero? @c))
      (do
        (print "\rremaining " @c "  ")
        (flush)
        (Thread/sleep 1000))
      (reset! c (count
                 (filter (comp not realized?)
                         fetched-map))))))

(defn -main
  [& args]
  (println "Starting requests to COE...")
  (start-threads (-> args first (BigInteger.)) (-> args second (BigInteger.)))
  (print-stats)
  (println "All reg-nos fetched! \n
Written to files...\n
Now you can hard-code the reg-no pattern in ./parse.sh and\n
run sh ./parse.sh <subcode1> <subcode2> ... <subcoden> > parsed.csv\n
and libreoffice parsed.csv to import to .ods\n
Hit Ctrl+C to exit..."))



;; [ WTF_Zen: cut/grep/tail/echo is not worse for this job :P ]
;; use parse.sh to parse

#_(defn parse-insane [file-base]
    (let [raw (parse (str "fetched-html/" file-base ".html"))
          table-with-name (->>
                           (-> raw (last) (nth 2) (nth 2))
                           (drop 3) first last)
          name-row (last (nth table-with-name 3))
          reg-no (-> name-row (nth 2) last last last)
          name (-> name-row (nth 3) last last last)
          mark-table-rows (->> (nth table-with-name 5)
                               (drop 2)
                               (drop 1))
          grades (map #(-> % (nth 3) last last) mark-table-rows)]
      (str reg-no ","
           name ","
           (reduce str (interpose "," grades)) ",")))
