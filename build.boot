(set-env!
  :resource-paths #{"src"}
  :source-paths #{"test"}
  :dependencies '[[org.clojure/clojure "1.7.0"     :scope "provided"]
                  [boot/core           "2.5.5"     :scope "provided"]
                  [http-kit            "2.1.19"    :scope "test"]
                  [adzerk/boot-test    "1.1.0"     :scope "test"]])

(require '[adzerk.boot-test :refer [test]])

(def +version+ "0.4.7-SNAPSHOT")

(task-options!
  pom {:project     'adzerk/boot-reload
       :version     +version+
       :description "Boot task to automatically reload page resources in the browser."
       :url         "https://github.com/adzerk/boot-reload"
       :scm         {:url "https://github.com/adzerk/boot-reload"}
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build []
  (comp
   (pom)
   (jar)
   (install)))

(deftask deploy []
  (comp
   (build)
   (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))

(deftask run-tests []
  (comp
    (test :namespaces #{'adzerk.boot-reload.server-test})))
