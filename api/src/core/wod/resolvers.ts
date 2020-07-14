import { Resolvers } from '~generated/graphql'

import { createWod } from './api/create'
import { wodById, wods } from './api/read'
import { workout } from '~core/workout'

export default <Resolvers>{
  Mutation: {
    createWod: (_, { input }, context) => createWod(context, input),
  },
  Query: {
    wods: (_, __, context) => wods(context),
  },
  Wod: {
    workout: (wod, _, context) => workout(context, wod.workoutId),
  },
}
