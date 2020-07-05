import { join } from 'path'
import { readFileSync } from 'fs'

export const schema = readFileSync(join(__dirname, 'schema.graphql'), 'utf8')
export { default as resolvers } from './resolvers'
export { ORMPersonalRecord } from './entities/personal-record'
export { ORMPersonalRecordType } from './entities/personal-record-type'
