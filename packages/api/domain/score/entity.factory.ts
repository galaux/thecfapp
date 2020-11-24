import { define } from 'typeorm-seeding'
import Faker from 'faker'

import { Score } from './entity'
import { ScoreType } from '../../generated/graphql'
import { User } from '../../domain/user'
import { Wod } from '../../domain/wod'

type SeedContext = {
  users: User[]
  wods: Wod[]
}

define(Score, (faker: typeof Faker, context?: SeedContext) => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const { users, wods } = context!
  const user = users[Math.floor(Math.random() * users.length)]
  const wod = wods[Math.floor(Math.random() * wods.length)]
  return new Score({
    type: faker.random.boolean() ? ScoreType.Repetitions : ScoreType.TimeMs,
    value: faker.random.number({ min: 1, max: 50 }),
    userId: user.id,
    wodId: wod.id,
  })
})
