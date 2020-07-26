# TheCFApp API

## dev environment setup

This will build and launch the back-end application in a standalone HTTP server
without trying to serve the front-end static files.

```
yarn install
cp .env.example .env
# Start a local PostgreSQL DB in line with the content of `.env`
yarn db:schema:sync
yarn db:seed:run
tsc --watch # Leave this process run in the term
nodemon build/index.js
```

## start the application in a live environment

```
yarn start
```

## Detail of Yarn scripts

- `build`: build project (generate GraphQL classes and transpile TypeScript)
- `codegen`: generate GraphQL classes
- `db:schema:drop`: drop the DB schema from DB described inside `ormconfig.yml`
- `db:schema:log`: show sql to be executed by db:schema:sync command
- `db:schema:sync`: create tables needed by the API in DB described inside
  `ormconfig.yml`
- `db:seed:config`: show DB seeding config
- `db:seed:run`: seed DB tables with dummy data
- `dev`: build project and start the API as a standalone HTTP server without
  trying to serve the front-end static files
- `start`: start the API inside a standalone HTTP server that also serves
  front-end static files
