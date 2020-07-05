import { personalRecordTypeById } from './core/personal-record/api/personal-record-type'
import { Context } from './type-defs'

export default {
  Query: {
    testMessage: (
      _: any,
      __: any,
      context: Context, // TODO
    ) => personalRecordTypeById(context),
  },
}
