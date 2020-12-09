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
│   ├── mobile/
│   │   └── package.json
│   └── server/
│       └── package.json
├── README.md
├── package.json
├── Procfile
└── yarn.lock
```

The _root_ `package.json` declares four Yarn workspaces in `./packages`:

- `api`: a [GraphQL](https://graphql.org/) API exposing domain objects living
  in a SQL database (see See [README.md](./packages/api/README.md))
- `front`: a Next.js web application targeted at mobiles (see
  [README.md](./packages/front/README.md))
- `mobile`: a React mobile [Progressive Web
  Application](https://web.dev/progressive-web-apps/)
- `server`: a [NodeJS Express](https://expressjs.com/) HTTP server to ship the
  `api` and either `front` or `mobile`

## GraphQL domain classes and React Hooks

The `api` GraphQL schema is written by hand and lives in
`@thecfapp/api/domain/**/*.graphql` files.  The `mobile` queries and fragments
are also written by hand and live in:
- `@thecfapp/mobile/src/graphql/`
- `@thecfapp/mobile/src/**/*.graphql`

A Yarn script generates the `api` domain classes and the `mobile` fragments and hooks.

## Scripts

- `build`: build the entire application
- `codegen`: (re)generate the GraphQL domain objects used in `api` and `mobile`
  from the GraphQL schema files
- `db:…` scripts: DB scripts to manipulate the DB described by the local `.env`
  (see the followings for details)
- `db:schema:drop`: drop the DB schema
- `db:schema:log`: log changes that would be applied to the DB if running
  `db:schema:sync`
- `db:schema:sync`: generate and persist the DB schema based on entities
- `db:seed:config`: display DB seeding configuration
- `db:seed:run`: seed random values in the DB
- `dev:api`: run the API in dev mode where code is hot-reloaded
- `dev:mobile`: run `mobile` application in dev mode where code is hot-reloaded
- `start`: run the whole application in production mode
- `test`: run all packages tests

When starting the server for the first time, you will want to:

1. install and start a PostgreSQL server
2. create a database `create database thecfapp`
3. copy `.env.example` to `.env` and customize its content depending on your
   PostgreSQL setup
4. run the following:

```
yarn build
yarn db:schema:sync
yarn db:seed:run
```

Once done, you can then:

- run tests: `yarn test`
- start the application: `yarn start`
- for development: start `api` and `mobile` in separate terms with `yarn
  dev:api` and `yarn dev:mobile`
- regenerate GraphQL domain classes and React Hooks with `yarn codegen`

Visit:

- the `mobile` application in production mode: http://localhost:4000/
- the `mobile` application in dev mode: http://localhost:3000/
- the GraphiQL UI: http://localhost:4000/graphql
- the Voyager UI: http://localhost:4000/voyager

## To-do

- [] Find a name

## Good to know

- If you need to install a dependency on a workspace, don't `cd` then `yarn
  install ${dep}` but run `yarn add workspace ${workspace} ${dep}`, this
  ensures said library is installed at the root rather than just the package.
  You might then need to `yarn install` at the root of the project.
