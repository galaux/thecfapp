import { Resolvers } from '../../generated/graphql'
import { createPersonalRecord, personalRecords } from './api/personal-record'
import {
  createPersonalRecordType,
  personalRecordTypeById,
  personalRecordTypes,
} from './api/personal-record-type'

export default <Resolvers>{
  Mutation: {
    createPersonalRecord: (_, { input }, context) =>
      createPersonalRecord(context, input),
    createPersonalRecordType: (_, { input }, context) =>
      createPersonalRecordType(context, input),
  },
  PersonalRecord: {
    type: (record, _, context) =>
      personalRecordTypeById(context, record.typeId),
  },
  Query: {
    personalRecords: (_, __, context) => personalRecords(context),
    personalRecordTypes: (_, __, context) => personalRecordTypes(context),
  },
}
