import { Resolvers } from '~generated/graphql'

import { communities } from './api/read'
import { createCommunity } from './api/create'

export default <Resolvers>{
  Mutation: {
    createCommunity: (_, { input }, context) => createCommunity(context, input),
  },
  Query: {
    communities: (_, __, context) => communities(context),
  },
}
