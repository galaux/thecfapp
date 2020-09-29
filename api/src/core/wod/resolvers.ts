import { Resolvers } from '../../generated/graphql'
import { createWod } from './api/create'
import { wods } from './api/read'
import { workout } from '../../core/workout'
import { community } from '../community'

export default <Resolvers>{
  Mutation: {
    createWod: (_, { input }, context) => createWod(context, input),
  },
  Query: {
    wods: (_, __, context) => wods(context),
  },
  Wod: {
    community: (wod, _, context) => community(context, wod.communityId),
    workout: (wod, _, context) => workout(context, wod.workoutId),
  },
}
