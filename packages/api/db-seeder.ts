import { Connection } from 'typeorm'
import { Factory, Seeder } from 'typeorm-seeding'

import { Community } from './domain/community'
import { Score } from './domain/score'
import { User } from './domain/user'
import { UserCommunity } from './domain/user-community'
import { Wod } from './domain/wod'
import { Workout } from './domain/workout'

export default class CreateWorkouts implements Seeder {
  public async run(factory: Factory, _: Connection): Promise<void> {
    const communities = await factory(Community)().createMany(5)
    const users = await factory(User)().createMany(30)

    await factory(UserCommunity)({
      communities,
      users,
    }).createMany(30)

    const workouts = await factory(Workout)().createMany(30)
    const wods = await factory(Wod)({
      communities,
      workouts,
    }).createMany(30)

    await factory(Score)({
      users,
      wods,
    }).createMany(50)

    // == Logged user
    const loggedUser = await factory(User)({
      userId: 'df56c5a2-3f0c-459a-b158-d1a999714247',
    }).create()

    communities.slice(0, 5).map(
      async (community) =>
        await factory(UserCommunity)({
          communities: [community],
          users: [loggedUser],
        }).create(),
    )

    wods.slice(0, 15).map(
      async (wod) =>
        await factory(Score)({
          users: [loggedUser],
          wods: [wod],
        }).create(),
    )
  }
}
