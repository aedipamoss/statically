(ns statically.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [markdown.core :as markdown])
  (:use selmer.parser)
  (:import org.apache.commons.io.FilenameUtils))

(defn get-files
  "Get a list of files from a given path"
  [path]
  (filter #(.isFile %) (file-seq (io/file path))))

(defn file-ext
  "Return the file extension of a given file"
  [file]
  (FilenameUtils/getExtension (.getName file)))

(defn file-base
  "Return the file basename of a given file"
  [file]
  (FilenameUtils/getBaseName (.getName file)))

(defn file-path
  "Return the file path of a given file"
  [file]
  (.toString (io/file file)))

(defn file-dest
  "Destination file to write to under 'output' directory"
  [output file]
  (str output "/" (file-base file) ".html"))

(defn to-html
  "Converts a file of markdown to html"
  [path]
  (with-open [reader (io/reader path)]
    (clojure.string/join (doall (map markdown/md-to-html-string (line-seq reader))))))

(defn render-with-template
  "Render the file with template to destination"
  [templates-path destination content]
  (selmer.parser/set-resource-path! templates-path)
  (spit destination (render-file "template.html" {:content content})))

(def template-dir
  "Use the 'templates' path under the current working directory"
  (str (System/getProperty "user.dir") "/templates"))

(def output-dir
  (str (System/getProperty "user.dir") "/output"))

(def source-dir
  (io/file (System/getProperty "user.dir") "blog"))

(defn generate-file
  "Output the file converting it from markdown to html"
  [file]
  (let [basename (file-base file)
        path (file-path file)
        destination (file-dest output-dir file)
        content (to-html path)]
    (println "Wrinting" basename "from" (file-path file) "to" destination)
    (render-with-template template-dir destination content)))

(defn -main
  "Entry point to our program."
  [& args]
  (doall (map generate-file (get-files source-dir))))
