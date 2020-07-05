import { schema as personalRecordSchema } from './core/personal-record'
import { readFileSync } from 'fs'

export default [
  readFileSync('src/schema.graphql', 'utf8'),
  personalRecordSchema,
]
