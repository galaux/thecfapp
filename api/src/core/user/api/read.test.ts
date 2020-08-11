import { Connection } from 'typeorm'
import { User } from '../entity'
import { factory, tearDownDatabase } from 'typeorm-seeding'
import { setupTestConnection } from '../../../util/test'
import { users } from '../../user'

describe('read users', () => {
  let connection: Connection

  beforeAll(async () => {
    connection = await setupTestConnection()
  })

  afterAll(async () => {
    await tearDownDatabase()
  })

  test('returns the list of users', async () => {
    const createdUser = await factory(User)().create()
    const res = await users({ conn: connection })
    expect(res).toEqual([
      {
        id: createdUser.id,
        name: createdUser.name,
      },
    ])
  })
})
