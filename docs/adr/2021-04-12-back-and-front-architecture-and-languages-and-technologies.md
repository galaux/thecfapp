## _Is GraphQL killing the usefuleness of a DB engine (SQL, Datomic or …)?_

When working with GraphQL it sometimes feel like we are nullifying the
usefuleness of a DB query engine because we don't have _complex_ (ie
_powerful_) queries but that should actually only be felt during the
development of the initial phase of a project, when one writes simple CRUD
queries/mutations. But when come the times to write real world complex business
queries, the DB engine will shine.

## _Is GraphQL killing the usefuleness of Datomic query engine over SQL?_

As simple CRUD GraphQL operations don't exactly use the query engine
intensively, one might think that it is overkill to use Datomic over SQL but
its usefuleness lies in its scalability features. Scaling a GraphQL backend
that relies on a SQL DB seems to be rather weak because it relies on caching
compared to Datomic inherent scalability feature. So though using Datomic won't
bring me anything *now*, if I need to scale later on, it should give me
scalability for free.

## Decisions about languages/technologies

I'm switching the DB to Datomic in order to gain the _scalability for free_ and
I am thus switching the backend to Clojure.

Don't use Fulco, because
- bad feedback from Oliver ("heavy" and can easily do without)
- seems a bit specific for my own experience (I'd rather have xp on raw Cljs, seems more employ-able)

Don't use Pathom, because
- seems too _specific_ too: would sort-of tie me to Cljs (though obviously one can actually use any front-end tech with it)


Let's go for
- Go ahead with Clojure+GraphQL migration of backend
- this will allow me do _delay_ the decision on what tech to use for front-end:
  - I can focus on the following target:
    "have a 1/1 replacement of the current TS API and have it work with the current TS mobile app"
    Then figure out if I want to keep the current TS/mobile app or rewrite it in Cljs
