import { Resolvers } from '../../generated/graphql'
import { community } from '../community'
import { createWod } from './api/create'
import { wodById, wods } from './api/read'
import { workout } from '../../domain/workout'

export default <Resolvers>{
  Mutation: {
    createWod: (_, { input }, context) => createWod(context, input),
  },
  Query: {
    wods: (_, __, context) => wods(context),
    wod: (_, { id }, context) => wodById(context, id),
  },
  Wod: {
    community: (wod, _, context) => community(context, wod.communityId),
    workout: (wod, _, context) => workout(context, wod.workoutId),
  },
}
