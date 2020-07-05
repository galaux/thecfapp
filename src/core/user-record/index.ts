import { join } from 'path'
import { readFileSync } from 'fs'

export const schema = readFileSync(join(__dirname, 'schema.graphql'), 'utf8')
export { default as resolvers } from './resolvers'
export { ORMUserRecord } from './entities/user-record'
export { ORMUserRecordType } from './entities/user-record-type'
