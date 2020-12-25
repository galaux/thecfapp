import {
  WodForWodCardFragment,
  WorkoutForWodCardFragment,
  ScoreForWodCardFragment,
} from '../WodCard'
import { ScoringType } from '../../types'
import { v4 as uuid } from 'uuid'
import faker from 'faker'
import { mapRandomTimes } from '../../test-utils'

function fakeScoreForWodCard(): ScoreForWodCardFragment {
  return {
    id: uuid(),
    value: faker.random.number({ min: 1, max: 1000 }),
    belongsToLoggedUser: false,
    user: {
      id: uuid(),
    },
  }
}

function fakeWodForWodCard(): WodForWodCardFragment {
  return {
    id: uuid(),
    date: new Date('2020-12-31'),
    scores: mapRandomTimes(fakeScoreForWodCard),
    workout: fakeWorkoutForWodCard(),
  }
}

function fakeWorkoutForWodCard(): WorkoutForWodCardFragment {
  return {
    id: uuid(),
    name: faker.random.words(5),
    description: faker.lorem.text(),
    scoringType: faker.random.boolean()
      ? ScoringType.DurationMs
      : ScoringType.Repetitions,
  }
}

export { fakeWodForWodCard, fakeWorkoutForWodCard, fakeScoreForWodCard }
