## Tips and tricks

- the REPL doesn't seem to detect when one removes the DB files on disk – which
  one can do to reinitialize the DB along with the schema for instance.
  Subsequent resart of the application at the REPL will yield an exception
  `"datomic db not found"`. The only way out seems to be to restart a REPL.
