import { Resolvers } from '../../generated/graphql'
import { communities, communitiesByUserId } from './api/read'
import { createCommunity } from './api/create'
import { wodsByCommunityId } from '../wod'

export default <Resolvers>{
  Community: {
    wods: (community, _, context) => wodsByCommunityId(context, community.id),
    // Require to resolve the `Address` GraphQL sub-type
    address: (community) => community,
  },
  Mutation: {
    createCommunity: (_, { input }, context) => createCommunity(context, input),
  },
  Query: {
    communities: (_, __, context) => communities(context),
    userCommunities: (_, { userId }, context) =>
      communitiesByUserId(context, userId),
  },
}
