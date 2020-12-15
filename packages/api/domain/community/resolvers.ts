import { Community } from './entity'
import { Resolvers } from '../../generated/graphql'
import { addUserToCommunity } from '../user/api/update'
import {
  communities,
  communitiesByUserId,
  lookupCommunities,
  memberCount,
} from './api/read'
import { createCommunity } from './api/create'
import { wodsByCommunityId } from '../wod'

export default <Resolvers>{
  Community: {
    // Require to resolve the `Address` GraphQL sub-type
    address: (community: Community) => community,
    memberCount: (community, __, context) => memberCount(context, community.id),
    wods: (community, _, context) => wodsByCommunityId(context, community.id),
  },
  Mutation: {
    createCommunity: async (_, { creatorId, input }, context) => {
      const newCommunity = await createCommunity(context, input)
      await addUserToCommunity(context, creatorId, newCommunity.id)
      return newCommunity
    },
  },
  Query: {
    communities: (_, __, context) => communities(context),
    lookupCommunities: (_, { name }, context) =>
      lookupCommunities(context, name),
    userCommunities: (_, { userId }, context) =>
      communitiesByUserId(context, userId),
  },
}
