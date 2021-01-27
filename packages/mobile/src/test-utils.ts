import { range } from 'lodash'
import faker from 'faker'
import {
  CommunityAddressFieldsFragment,
  CommunityFieldsFragment,
  ScoreFieldsFragment,
  WodFieldsFragment,
  WorkoutFieldsFragment,
} from './graphql/fragment/fields.generated'
import { v4 as uuid } from 'uuid'
import { ScoringType } from './types'

export function fakeCommunityFieldsFragment(): CommunityFieldsFragment {
  return {
    id: uuid(),
    name: faker.random.words(5),
  }
}

export function fakeCommunityAddressFieldsFragment(): CommunityAddressFieldsFragment {
  return {
    street: faker.address.streetAddress(),
    zipCode: faker.address.zipCode(),
    city: faker.address.city(),
    country: faker.address.country(),
  }
}

export function fakeScoreFieldsFragment(): ScoreFieldsFragment {
  return {
    id: uuid(),
    value: faker.random.number({ min: 1, max: 1000 }),
  }
}

export function fakeWodFieldsFragment(): WodFieldsFragment {
  return {
    id: uuid(),
    date: new Date('2020-12-31'),
  }
}

export function fakeWorkoutFieldsFragment(): WorkoutFieldsFragment {
  return {
    id: uuid(),
    name: faker.random.words(5),
    description: faker.lorem.text(),
    scoringType: faker.random.boolean()
      ? ScoringType.DurationMs
      : ScoringType.Repetitions,
  }
}

export type RandomTimesOpts = { min?: number; max?: number; precision?: number }

export function mapRandomTimes<R>(
  f: () => R,
  opts: RandomTimesOpts = { min: 1, max: 5 },
) {
  return range(faker.random.number(opts)).map(f)
}

export function withoutConsoleErrors(f: () => void) {
  const logError = console.error
  console.error = () => null
  f()
  console.error = logError
}
