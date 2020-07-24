import { readFileSync } from 'fs'

export default [
  'src/core/community/schema.graphql',
  'src/core/score/schema.graphql',
  'src/core/user/schema.graphql',
  'src/core/wod/schema.graphql',
  'src/core/workout/schema.graphql',
  'src/schema.graphql',
].map((path) => readFileSync(path, 'utf8'))
