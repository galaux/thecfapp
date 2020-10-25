## 2020-07-04 What DB to use?

Let's go with what I know: SQL with PG. That will enable me to

- build on my experience from `graphql-gateway`
- start fast (not have to learn a new DB)
- keep a lot of options open if I need to move to some other env such as AWS
  which supports PG and SQL DBs

## 2020-07-04 Should I use something rather evolved such as Prisma/Hasura?

A: Honestly, given the experience we have on `graphql-gateway` it seems the
data layer can – and should? – be as thin as possible so let's go for raw read
of SQL tables and see later if I want something more evoluted.
