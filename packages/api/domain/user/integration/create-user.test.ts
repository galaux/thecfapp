import { ApolloServerTestClient } from 'apollo-server-testing'
import { readOperation, setupTest, uuidRe } from '../../../test-utils'
import { tearDownDatabase } from 'typeorm-seeding'

describe('creating a user', () => {
  let client: ApolloServerTestClient

  beforeAll(async () => {
    client = await setupTest()
  })

  afterAll(async () => {
    await tearDownDatabase()
  })

  test('returns the created user', async () => {
    const res = await client.mutate({
      mutation: readOperation('create-user'),
      variables: { name: 'Billy Bob' },
    })

    // TODO should we check the DB actually contains the record?

    expect(res).toEqual(
      expect.objectContaining({
        data: {
          createUser: {
            id: expect.stringMatching(uuidRe),
            name: 'Billy Bob',
          },
        },
        errors: undefined,
      }),
    )
  })
})
