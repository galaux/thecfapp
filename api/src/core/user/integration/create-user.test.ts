import { ApolloServerTestClient } from 'apollo-server-testing'
import { gql } from 'apollo-server-express'
import { setupTestServer, uuidRe } from '../../../util/test'
import { tearDownDatabase } from 'typeorm-seeding'

describe('creating a user', () => {
  let server: ApolloServerTestClient

  beforeAll(async () => {
    server = await setupTestServer()
  })

  afterAll(async () => {
    await tearDownDatabase()
  })

  test('returns the created user', async () => {
    const { mutate } = server

    const CREATE_USER = gql`
      mutation CreateUser($name: String!) {
        createUser(input: { name: $name }) {
          id
          name
        }
      }
    `

    const res = await mutate({
      mutation: CREATE_USER,
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
