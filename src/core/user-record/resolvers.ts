import { Resolvers } from '../../generated/graphql'
import { createUserRecord, userRecords } from './api/user-record'
import {
  createUserRecordType,
  userRecordTypeById,
  userRecordTypes,
} from './api/user-record-type'

export default <Resolvers>{
  Mutation: {
    createUserRecord: (_, { input }, context) =>
      createUserRecord(context, input),
    createUserRecordType: (_, { input }, context) =>
      createUserRecordType(context, input),
  },
  UserRecord: {
    type: (record, _, context) => userRecordTypeById(context, record.typeId),
  },
  Query: {
    userRecords: (_, __, context) => userRecords(context),
    userRecordTypes: (_, __, context) => userRecordTypes(context),
  },
}
