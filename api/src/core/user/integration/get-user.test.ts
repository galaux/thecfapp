import { ApolloServerTestClient } from 'apollo-server-testing'
import { User } from '../entity'
import { factory, tearDownDatabase } from 'typeorm-seeding'
import { readOperation, setupTestServer } from '../../../util/test'

describe('Get users', () => {
  let server: ApolloServerTestClient

  beforeAll(async () => {
    server = await setupTestServer()
  })

  afterAll(async () => {
    await tearDownDatabase()
  })

  test('returns the requested user', async () => {
    const createdUser = await factory(User)().create()

    const { query } = server
    const res = await query({
      query: readOperation('get-user'),
      variables: { id: createdUser.id },
    })

    expect(res).toEqual(
      expect.objectContaining({
        data: {
          user: expect.objectContaining({
            id: createdUser.id,
            name: createdUser.name,
          }),
        },
        errors: undefined,
      }),
    )
  })
})
