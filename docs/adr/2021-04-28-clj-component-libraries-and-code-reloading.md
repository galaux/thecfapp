Big questions are
- should I use `integrant` or `mount`
- how to get code reload **in tests**

I now understand that `wrap-reload` uses `ns-tracker` and because of that to
get it to work, one needs to have one global static system that instanciates
`wrap-reload` once to watch the files, then I change the code that will be
picked up by the instance of wrap-reload. But what won't work is if I
keep instanciating `wrap-reload` again and again as I do in my tests.

To be able to use `wrap-reload` in test, I would need some sort of global
system for tests – because that would mean running only once `wrap-reload`
which will finally be able to do its job – but that would mean I would have
this system for test **always** up and running which makes no sense. This is a
clear indication to me that `wrap-reload` is meant to be used in dev (a local
dev system declared in `user.clj` and started/halted manually by the user) but
not for test. **I should not try to setup `wrap-reload` for my tests**.

In order to setup code reloading in test, I should rather have a look at why
Cider doesn't pick the code changes.

On a side note, it now seems rather overkill to spawn a full new system for
each test (as this is currently done in my `integrant` version). A single
`test` ("singleton"?) system to which I `swap` the statefull components (`db`)
should suffice. My question is then: taken this into consideration, isn't
`integrant` sort of overkill compared to `mount`+`yurt`?

I remember switching from `mount` to `integrant` because when experimenting
with `yurt` in when running tests in parallel in the REPL, reads and writes
were colliding (read of a test returned the write of somme other test) even
though I was using `yurt.` I expected to get rid of this by switching to
`integrant` where I could have _one system for each test_. When I switched to
`integrant` I obviously experienced the same problem because no matter how many
different systems you fire-up, they all end-up reading/writing on the same DB!
I fixed that by using a different DB for each test … which I could have done
similarly with `mount` inside the `yurt`. In the end, I didn't have to switch
to `integrant`.

Let's:
- figure out why Cider doesn't reload my code between 2 test runs
- keep the current `integrant` integration for now
- potentially switch to using `mount` (taking into consideration that I have
  some nice `integrant`/`aero` integration)

I've found why when running Cider's `cider-ns-refresh` which seems to boil down
to 2 things:
- I had old code in a now unused file (`schema.clj`) referencing a now
  inexisting other file (`api/db.clj`). When running
  `clojure.tools.namespace.repl/refresh` from the REPL, I got an exception
  stating this `schema.clj` had a missing dependency `api/db.clj`. As it seems
  Cider's `cider-ns-refresh` uses tools.namespace, it must actually be failing
  silently and thus not reloading.
- It seems [there is a problem with
  tools.namespace](https://github.com/clojure-emacs/cider/issues/2686#issuecomment-532389499)
  that can be worked around by adding
  `(clojure.tools.namespace.repl/set-refresh-dirs "dev" "src" "test")` to
  `user.clj` and loading it before tests.

With these old files removed and this `set-refresh-dirs` in `user.clj`, when I
load `user.clj`, I just have to save my new code, run `cider-ns-refresh` (M-m m
enr) and the new code is run.
