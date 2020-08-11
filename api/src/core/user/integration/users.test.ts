import { ApolloServerTestClient } from 'apollo-server-testing'
import { User } from '../entity'
import { factory, tearDownDatabase } from 'typeorm-seeding'
import { gql } from 'apollo-server-express'
import { setupTestServer } from '../../../util/test'

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

    const GET_USERS = gql`
      {
        users {
          id
          name
        }
      }
    `

    const { query } = server
    const res = await query({ query: GET_USERS })

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
