import { loggedUserId } from '../../graphql/state/app'
import {
  UserCommunitiesDocument,
  UserCommunitiesQuery,
  UserCommunitiesQueryVariables,
  UserCommunityFragment,
} from './user-communities.generated'
import { ApolloCache } from '@apollo/client'

/* Watch out when crafting new cache data: be sure to include all fields
   and specifically `id`.
   Apollo sometimes warns about `Cache data may be lost when replacing
   the userCommunities field of a Query object.` which it must do because
   it can't tell for sure we didn't messed-up the cache write (its advice
   to define a custome `merge` function for `Query.userCommunities` won't
   work because we don't want following results of the "user communities"
   query to be merged into the previous cached ones). This warning should
   thus be safe to ignore. Also see `assumeImmutableResults`. */

export function addCommunityToUserCommunitiesCache<T>(
  cache: ApolloCache<T>,
  newCommunity: UserCommunityFragment | undefined,
) {
  if (!newCommunity) return
  const cacheData = cache.readQuery<
    UserCommunitiesQuery,
    UserCommunitiesQueryVariables
  >({
    query: UserCommunitiesDocument,
    variables: { userId: loggedUserId() },
  })
  if (!cacheData) return
  const newCacheData = {
    ...cacheData,
    userCommunities: [...cacheData.userCommunities, newCommunity],
  }
  cache.writeQuery<UserCommunitiesQuery, UserCommunitiesQueryVariables>({
    query: UserCommunitiesDocument,
    variables: { userId: loggedUserId() },
    data: newCacheData,
  })
}

export function removeCommunityFromUserCommunitiesCache<T>(
  cache: ApolloCache<T>,
  communityId: string | undefined,
) {
  if (!communityId) return
  const cacheData = cache.readQuery<
    UserCommunitiesQuery,
    UserCommunitiesQueryVariables
  >({
    query: UserCommunitiesDocument,
    variables: { userId: loggedUserId() },
  })
  if (!cacheData) return
  const newCacheData = {
    ...cacheData,
    userCommunities: cacheData.userCommunities.filter(
      (c) => c.id !== communityId,
    ),
  }
  cache.writeQuery<UserCommunitiesQuery, UserCommunitiesQueryVariables>({
    query: UserCommunitiesDocument,
    variables: { userId: loggedUserId() },
    data: newCacheData,
  })
}
