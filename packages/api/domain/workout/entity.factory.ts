import { define } from 'typeorm-seeding'
import Faker from 'faker'

import { ScoringType } from '../../generated/graphql'
import { Workout } from './entity'

define(Workout, (faker: typeof Faker) => {
  const workout = new Workout({
    name: faker.random.words(3),
    description: faker.lorem.text(),
    scoringType: faker.random.boolean()
      ? ScoringType.Repetitions
      : ScoringType.DurationMs,
  })
  return workout
})
