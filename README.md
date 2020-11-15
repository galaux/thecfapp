# TheCFApp

## Repository organisation

This Git repository is a monorepo that leverages Yarn's workspaces as per the
following file hierarchy:

```
.
├── docs/
├── packages/
│   ├── api/
│   │   └── package.json
│   ├── front/
│   │   └── package.json
│   └── server/
│       └── package.json
├── README.md
├── package.json
├── Procfile
└── yarn.lock
```

The _root_ `package.json` declares three Yarn workspaces in `./packages`:

- `api`: a [GraphQL](https://graphql.org/) API exposing domain objects living
  in a SQL database (see See [README.md](./packages/api/README.md))
- `front`: a Next.js web application targeted at mobiles (see
  [README.md](./packages/front/README.md))
- `server`: a [NodeJS Express](https://expressjs.com/) HTTP server to ship the
  `api` and the `front`

## Scripts

- `build`: build the entire application
- `codegen`: (re)generate the GraphQL domain objects from the GraphQL schema
  files
- `db:…` scripts: by default, apply to the local DB. Add `-c ${ENV}`
  with `ENV` from `./packages/api/ormconfig.yml` to apply to said environment.
- `db:schema:drop`: drop the DB schema
- `db:schema:log`: log changes that would be applied to the DB if running
  `db:schema:sync`
- `db:schema:sync`: generate and apply the DB schema
- `db:seed:config`: display DB seeding configuration
- `db:seed:run`: seed random values in the DB
- `dev`: run the whole application in dev mode where code is hot-reloaded
- `start`: run the whole application in production mode
- `test`: run all packages tests

```
cp .env.example .env
yarn build
# Start a local PostgreSQL DB in line with the content of `.env`
# Create a database `create database thecfapp`
yarn db:schema:sync
yarn db:seed:run
yarn dev
```

Visit:

- the web application: http://localhost:4000/login
- the GraphiQL UI: http://localhost:4000/graphql
- the Voyager UI: http://localhost:4000/voyager

## To-do

- [] Find a name
