import { Resolvers } from '../../generated/graphql'
import { addUserToCommunity } from './api/update'
import { communitiesByUserId } from '../../domain/community'
import { createUser } from './api/create'
import { users, user } from './api/read'

export default <Resolvers>{
  Mutation: {
    createUser: (_, { input }, context) => createUser(context, input),
    addUserToCommunity: (_, { input }, context) =>
      addUserToCommunity(context, input),
  },
  Query: {
    user: (_, { id }, context) => user(context, id),
    users: (_, __, context) => users(context),
  },
  User: {
    communities: (user, _, context) => communitiesByUserId(context, user.id),
  },
}
