import { ApolloServerTestClient } from 'apollo-server-testing'
import { User } from '../entity'
import { factory, tearDownDatabase } from 'typeorm-seeding'
import { readOperation, setupTestServer } from '../../../test-utils'

describe('Get users', () => {
  let server: ApolloServerTestClient

  beforeAll(async () => {
    server = await setupTestServer()
  })

  afterAll(async () => {
    await tearDownDatabase()
  })

  test('returns the list of users', async () => {
    const createdUsers = await factory(User)().createMany(5)

    const { query } = server
    const res = await query({ query: readOperation('get-users') })

    expect(res.data!.users!.length).toBe(5)
    expect(res).toEqual(
      expect.objectContaining({
        data: {
          users: expect.arrayContaining([
            {
              id: createdUsers[0].id,
              name: createdUsers[0].name,
            },
          ]),
        },
        errors: undefined,
      }),
    )
  })
})
