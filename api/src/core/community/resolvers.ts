import { Resolvers } from '../../generated/graphql'
import { communities } from './api/read'
import { createCommunity } from './api/create'
import { wodsByCommunityId } from '../wod'

export default <Resolvers>{
  Community: {
    wods: (community, _, context) => wodsByCommunityId(context, community.id),
  },
  Mutation: {
    createCommunity: (_, { input }, context) => createCommunity(context, input),
  },
  Query: {
    communities: (_, __, context) => communities(context),
  },
}
