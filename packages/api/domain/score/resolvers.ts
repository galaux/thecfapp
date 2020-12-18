import { Resolvers } from '../../generated/graphql'
import { createScore } from './api/create'
import { scores } from './api/read'
import { user } from '../../domain/user'
import { wodById } from '../../domain/wod'

export default <Resolvers>{
  Mutation: {
    createScore: async (_, { input }, context) => createScore(context, input),
  },
  Query: {
    scores: (_, __, context) => scores(context),
  },
  Score: {
    user: (score, _, context) => user(context, score.userId),
    value: (score) => score.value,
    wod: (score, _, context) => wodById(context, score.wodId),
  },
}
