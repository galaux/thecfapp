import { Connection } from 'typeorm'
import { Factory, Seeder } from 'typeorm-seeding'

import { Community } from './core/community'
import { Score } from './core/score'
import { User } from './core/user'
import { UserCommunity } from './core/user-community'
import { Wod } from './core/wod'
import { Workout } from './core/workout'

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
