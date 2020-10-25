import { ApolloServerTestClient } from 'apollo-server-testing'
import { User } from '../entity'
import { factory, tearDownDatabase } from 'typeorm-seeding'
import { readOperation, setupTestServer } from '../../../test-utils'
import { UserCommunity } from '../../user-community'
import { Community } from '../../community'
import { Wod } from '../../wod'
import { Workout } from '../../workout'

describe('Get users', () => {
  let server: ApolloServerTestClient

  beforeAll(async () => {
    server = await setupTestServer()
  })

  afterAll(async () => {
    await tearDownDatabase()
  })

  test('returns the requested user', async () => {
    /* TODO should we factorize and generate these once and for all by just
       "making" them (without persistance) and then each test could pick the
       ones it wants and persist them? */
    const communities = await factory(Community)().createMany(2)
    const createdUser: User = await factory(User)().create()
    for (const community of communities) {
      await factory(UserCommunity)({
        communities: [community],
        users: [createdUser],
      }).create()
    }
    const workouts = await factory(Workout)().createMany(3)
    const wodCommunity1 = await factory(Wod)({
      communities: [communities[0]],
      workouts: [workouts[0]],
    }).create()
    const wod1Community2 = await factory(Wod)({
      communities: [communities[1]],
      workouts: [workouts[1]],
    }).create()
    const wod2Community2 = await factory(Wod)({
      communities: [communities[1]],
      workouts: [workouts[2]],
    }).create()

    const { query } = server
    const res = await query({
      query: readOperation('get-user'),
      variables: { id: createdUser.id },
    })
    expect(res).toMatchObject({
      data: {
        user: {
          name: createdUser.name,
          communities: [
            {
              id: communities[0].id,
              name: communities[0].name,
              wods: [
                {
                  id: wodCommunity1.id,
                  date: wodCommunity1.date.toString(),
                },
              ],
            },
            {
              id: communities[1].id,
              name: communities[1].name,
              wods: [
                {
                  id: wod1Community2.id,
                  date: wod1Community2.date.toString(),
                },
                {
                  id: wod2Community2.id,
                  date: wod2Community2.date.toString(),
                },
              ],
            },
          ],
        },
      },
      errors: undefined,
    })
  })
})
