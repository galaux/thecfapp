import { Resolvers } from '../../generated/graphql'
import { addUserToCommunity, removeUserFromCommunity } from './api/update'
import { communitiesByUserId, community } from '../../domain/community'
import { createUser } from './api/create'
import { user, users } from './api/read'

export default <Resolvers>{
  Mutation: {
    createUser: (_, { input }, context) => createUser(context, input),
    addUserToCommunity: async (
      _,
      { input: { userId, communityId } },
      context,
    ) => {
      await addUserToCommunity(context, userId, communityId)
      return community(context, communityId)
    },
    removeUserFromCommunity: async (
      _,
      { input: { userId, communityId } },
      context,
    ) => {
      await removeUserFromCommunity(context, userId, communityId)
      return communityId
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
