import { schema as userRecordSchema } from './core/user-record'
import { readFileSync } from 'fs'

export default [readFileSync('src/schema.graphql', 'utf8'), userRecordSchema]
