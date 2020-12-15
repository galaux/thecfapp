import { Resolvers } from '../../generated/graphql'
import { addUserToCommunity, removeUserFromCommunity } from './api/update'
import { communitiesByUserId, community } from '../../domain/community'
import { createUser } from './api/create'
import { user, users } from './api/read'

export default <Resolvers>{
  Mutation: {
    createUser: (_, { input }, context) => createUser(context, input),
    addUserToCommunity: async (_, { input }, context) => {
      await addUserToCommunity(context, input)
      return community(context, input.communityId)
    },
    removeUserFromCommunity: async (_, { input }, context) => {
      await removeUserFromCommunity(context, input)
      return input.communityId
    },
  },
  Query: {
    user: (_, { id }, context) => user(context, id),
    users: (_, __, context) => users(context),
  },
  User: {
    communities: (user, _, context) => communitiesByUserId(context, user.id),
  },
}
