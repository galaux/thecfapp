import { define } from 'typeorm-seeding'
import Faker from 'faker'

import { Community } from '~core/community'
import { Workout } from '~core/workout'

import { LocalDate } from '@js-joda/core'
import { Wod } from './entity'

type SeedContext = {
  communities: Community[]
  workouts: Workout[]
}

define(Wod, (faker: typeof Faker, context?: SeedContext) => {
  const { workouts } = context!
  const workout = workouts[Math.floor(Math.random() * workouts.length)]
  return new Wod({
    date: LocalDate.ofYearDay(2020, faker.random.number({ min: 1, max: 365 })),
    workoutId: workout.id,
  })
})
