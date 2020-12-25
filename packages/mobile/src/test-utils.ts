import { range } from 'lodash'
import faker from 'faker'

export type RandomTimesOpts = { min?: number; max?: number; precision?: number }

export function mapRandomTimes<R>(
  f: () => R,
  opts: RandomTimesOpts = { min: 0, max: 5 },
) {
  return range(faker.random.number(opts)).map(f)
}
