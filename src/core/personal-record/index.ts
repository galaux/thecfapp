import { readFileSync } from 'fs'
import { join } from 'path'

export const schema = readFileSync(join(__dirname, 'schema.graphql'), 'utf8')
export { default as resolvers } from './resolvers'
export { PersonalRecord } from './entities/personal-record'
export { PersonalRecordType } from './entities/personal-record-type'
