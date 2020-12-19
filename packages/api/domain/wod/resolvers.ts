import { Resolvers } from '../../generated/graphql'
import { community } from '../community'
import { createWod } from './api/create'
import { userWods, wodById } from './api/read'
import { wodScores } from '../score/api/read'
import { workout } from '../../domain/workout'

export default <Resolvers>{
  Mutation: {
    createWod: (_, { input }, context) => createWod(context, input),
  },
  Query: {
    userWods: (_, { userId }, context) => userWods(context, userId),
    wod: (_, { id }, context) => wodById(context, id),
  },
  Wod: {
    community: (wod, _, context) => community(context, wod.communityId),
    scores: (wod, _, context) => wodScores(context, wod.id),
    workout: (wod, _, context) => workout(context, wod.workoutId),
  },
}
