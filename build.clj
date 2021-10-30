(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'cfapp)
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def uber-file (format "target/%s.jar" (name lib)))

(defn clean [_]
  (b/delete {:path "target"}))

(defn uber [_]
  (clean nil)
  (b/copy-dir {:src-dirs ["src/clj"
                          "resources"]
               :target-dir class-dir})
  (b/copy-dir {:src-dirs ["views/public"]
               :ignores ["index.html"]
               :target-dir (str class-dir "/public")})
  (b/compile-clj {:basis basis
                  :src-dirs ["src/clj"]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis basis
           :main 'cfapp.api.core}))
