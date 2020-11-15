import { Connection } from 'typeorm'
import { Factory, Seeder } from 'typeorm-seeding'

import { Community } from './domain/community'
import { Score } from './domain/score'
import { User } from './domain/user'
import { UserCommunity } from './domain/user-community'
import { Wod } from './domain/wod'
import { Workout } from './domain/workout'

export default class CreateWorkouts implements Seeder {
  public async run(factory: Factory, _: Connection): Promise<any> {
    const communities = await factory(Community)().createMany(5)
    const users = await factory(User)().createMany(30)

    const usersCommunities = await factory(UserCommunity)({
      communities,
      users,
    }).createMany(30)

    const workouts = await factory(Workout)().createMany(30)
    const wods = await factory(Wod)({
      communities,
      workouts,
    }).createMany(30)

    const scores = await factory(Score)({
      users,
      wods,
    }).createMany(50)
  }
}
