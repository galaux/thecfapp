import { readFileSync } from 'fs'

export default [
  // TODO API paths but relative to `server` package
  '../api/domain/community/schema.graphql',
  '../api/domain/score/schema.graphql',
  '../api/domain/user/schema.graphql',
  '../api/domain/wod/schema.graphql',
  '../api/domain/workout/schema.graphql',
  '../api/schema.graphql',
].map((path) => readFileSync(path, 'utf8'))
