import { userRecordTypeById } from './core/user/api/user-record-type'
import { Context } from './type-defs'

export default {
  Query: {
    testMessage: (
      _: any,
      __: any,
      context: Context, // TODO
    ) => userRecordTypeById(context),
  },
}
