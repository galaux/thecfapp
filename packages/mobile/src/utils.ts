import { sortBy } from 'lodash/fp'
import { Address } from './types'
import { ScoreFieldsFragment } from './graphql/fragment/fields.generated'

export const COPYRIGHT_STR = '© 2020 TheCFApp. All rights reserved'

export function oneLineAddress(address?: Address | null): string {
  return (
    (address?.street ?? '') +
    (address?.zipCode ?? '') +
    (address?.city ?? '') +
    (address?.country ?? '')
  ).trim()
}

// TODO use enum taken from GraphQL types
export function scoringTypeLabel(type: string) {
  switch (type) {
    case 'REPETITIONS': {
      return 'repetitions'
    }
    case 'DURATION_MS': {
      return 'ms'
    }
  }
}

// FIXME use enum for workoutType
export function sortScores<Score extends ScoreFieldsFragment>(
  workoutType: string,
  scores: Score[],
): Score[] {
  const result = sortBy((s) => s.value, scores)
  switch (workoutType) {
    case 'REPETITIONS': {
      return result.reverse()
    }
    case 'DURATION_MS': {
      return result
    }
    default: {
      throw Error(`Unknown workout type: ${workoutType}`)
    }
  }
}
