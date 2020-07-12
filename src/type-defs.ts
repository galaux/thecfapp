import { readFileSync } from 'fs'

export default [
  'src/core/user-record/schema.graphql',
  'src/schema.graphql',
].map((path) => readFileSync(path, 'utf8'))
