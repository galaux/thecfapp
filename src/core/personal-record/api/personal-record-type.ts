import { PersonalRecordType } from '../entities/personal-record-type'
import { Context } from '../../../type-defs'

async function personalRecordTypeById(context: Context): Promise<string> {
  const { conn } = context
  console.log('Inserting a new user into the database...')
  const recordType = new PersonalRecordType({ name: 'haha' })
  await conn.manager.save(recordType)
  console.log('Saved a new user with id: ' + recordType.id)

  console.log('Loading users from the database...')
  const recordTypes = await conn.manager.find(PersonalRecordType)
  console.log('Loaded users: ', recordTypes)

  console.log('Here you can setup and run express/koa/any other framework.')

  return 'res'
}

export { personalRecordTypeById }
