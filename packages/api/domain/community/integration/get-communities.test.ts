import { ApolloServerTestClient } from 'apollo-server-testing'
import { readOperation, setupTest, uuidRe } from '../../../test-utils'
import { runSeeder, tearDownDatabase } from 'typeorm-seeding'
import CreateWorkouts from '../../../db-seeder'

describe('get communities', () => {
  let client: ApolloServerTestClient

  beforeAll(async () => {
    client = await setupTest()
  })

  afterAll(async () => {
    await tearDownDatabase()
  })

  test('returns the list of communities', async () => {
    await runSeeder(CreateWorkouts)
    const res = await client.query({ query: readOperation('get-communities') })

    expect(res.data!.communities!.length).toBeGreaterThan(0)
    expect(res).toEqual(
      expect.objectContaining({
        data: {
          communities: expect.arrayContaining([
            {
              id: expect.stringMatching(uuidRe),
              name: expect.any(String),
            },
          ]),
        },
        errors: undefined,
      }),
    )
  })
})
