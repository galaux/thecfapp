import { Resolvers } from '~generated/graphql'

import { user } from '~core/user'
import { wodById } from '~core/wod'

import { createScore } from './api/create'
import { scores } from './api/read'

export default <Resolvers>{
  Mutation: {
    createScore: (_, { input }, context) => createScore(context, input),
  },
  Query: {
    scores: (_, __, context) => scores(context),
  },
  Score: {
    type: (score) => score.type,
    user: (score, _, context) => user(context, score.userId),
    value: (score) => score.value,
    wod: (score, _, context) => wodById(context, score.wodId),
  },
}
