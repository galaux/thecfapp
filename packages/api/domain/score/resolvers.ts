import { Resolvers } from '../../generated/graphql'
import { createScore } from './api/create'
import { user } from '../../domain/user'
import { wodById } from '../../domain/wod'
import { wodScores } from './api/read'

export default <Resolvers>{
  Mutation: {
    createScore: async (_, { input }, context) => createScore(context, input),
  },
  Query: {
    scores: (_, { wodId }, context) => wodScores(context, wodId),
  },
  Score: {
    user: (score, _, context) => user(context, score.userId),
    value: (score) => score.value,
    wod: (score, _, context) => wodById(context, score.wodId),
  },
}
