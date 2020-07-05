import {
  createPersonalRecordType,
  personalRecordTypeById,
  personalRecordTypes,
} from './api/personal-record-type'
import { personalRecords, createPersonalRecord } from './api/personal-record'
import { Resolvers } from '../../generated/graphql'

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
