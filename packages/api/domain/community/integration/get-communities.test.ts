import { ApolloServerTestClient } from 'apollo-server-testing'
import { readOperation, setupTestServer, uuidRe } from '../../../test-utils'
import { runSeeder, tearDownDatabase } from 'typeorm-seeding'
import CreateWorkouts from '../../../db-seeder'

describe('get communities', () => {
  let server: ApolloServerTestClient

  beforeAll(async () => {
    server = await setupTestServer()
  })

  afterAll(async () => {
    await tearDownDatabase()
  })

  test('returns the list of communities', async () => {
    await runSeeder(CreateWorkouts)
    const { query } = server
    const res = await query({ query: readOperation('get-communities') })

    expect(res.data!.communities!.length).toBeGreaterThan(0)
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
