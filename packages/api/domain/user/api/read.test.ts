import { Context } from '../../../types'
import { User } from '../entity'
import { factory, tearDownDatabase } from 'typeorm-seeding'
import { setupTestContext } from '../../../test-utils'
import { users } from '../../user'

describe('read users', () => {
  let context: Context

  beforeAll(async () => {
    context = await setupTestContext()
  })

  afterAll(async () => {
    await tearDownDatabase()
  })

  test('returns the list of users', async () => {
    const createdUser = await factory(User)().create()
    const res = await users(context)
    expect(res).toEqual([
      {
        id: createdUser.id,
        name: createdUser.name,
      },
    ])
  })
})
