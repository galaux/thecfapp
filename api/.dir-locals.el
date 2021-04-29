;;((clojure-mode . ((cider-lein-global-options . "with-profile test"))))
;((clojure-mode
;   (cider-clojure-cli-global-options . "-M:dev")))
((clojure-mode . ((cider-preferred-build-tool . clojure-cli)
                  (cider-clojure-cli-global-options . "-A:dev:test"))))
