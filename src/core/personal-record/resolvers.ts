import { personalRecordTypeById } from './api/personal-record-type'
import { Resolvers } from '../../generated/graphql'

export default <Resolvers>{
  Query: {
    testMessage: (_, __, context) => personalRecordTypeById(context),
  },
}
