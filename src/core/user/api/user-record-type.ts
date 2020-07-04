import { UserRecordType } from '../entities/user-record-type'
import { Context } from '../../../type-defs'

async function userRecordTypeById(context: Context): Promise<string> {
  const { conn } = context
  console.log('Inserting a new user into the database...')
  const recordType = new UserRecordType({ name: 'haha' })
  await conn.manager.save(recordType)
  console.log('Saved a new user with id: ' + recordType.id)

  console.log('Loading users from the database...')
  const recordTypes = await conn.manager.find(UserRecordType)
  console.log('Loaded users: ', recordTypes)

  console.log('Here you can setup and run express/koa/any other framework.')

  return 'res'
}

export { userRecordTypeById }
