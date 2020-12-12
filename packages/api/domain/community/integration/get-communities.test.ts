import { ApolloServerTestClient } from 'apollo-server-testing'
import { Community } from '../entity'
import { factory, tearDownDatabase } from 'typeorm-seeding'
import { readOperation, setupTest, uuidRe } from '../../../test-utils'

describe('get communities', () => {
  let client: ApolloServerTestClient

  beforeAll(async () => {
    client = await setupTest()
  })

  afterAll(async () => {
    await tearDownDatabase()
  })

  test('returns the list of communities', async () => {
    await factory(Community)().createMany(5)
    const res = await client.query({ query: readOperation('get-communities') })

    expect(res.data?.communities?.length).toBe(5)
    expect(res).toEqual(
      expect.objectContaining({
        data: {
          communities: expect.arrayContaining([
            expect.objectContaining({
              id: expect.stringMatching(uuidRe),
              name: expect.any(String),
            }),
          ]),
        },
        errors: undefined,
      }),
    )
  })
})
