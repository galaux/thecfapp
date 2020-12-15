import { Layout, Header, Body } from '../Layout'
import { RouteComponentProps } from '@reach/router'
import { FindCommunityPanel } from './FindCommunityPanel'
import { loggedUserId } from '../graphql/state/app'
import {
  useLookupCommunitiesLazyQuery,
  useJoinCommunityMutation,
  useUserCommunitiesQuery,
  useLeaveCommunityMutation,
  UserCommunitiesDocument,
  UserCommunitiesQuery,
  UserCommunitiesQueryVariables,
} from './find-community.generated'
import { Spinner } from '@chakra-ui/react'
import { fetchPolicy } from '../fetch-policy'

const ONE_MINUTE_IN_MS = 60 * 1000

/* The logic of this page and its components is as follows:

   This page immediately runs a "user communities" query to ensure its result
   is cached.

   Then, when user does a search, we perform a "community lookup" query which
   result contains locally computed field `isMember @client`. This field's
   computation – declared as a `typePolicies` at cache instanciation – is
   based on the result of the "user communities".

   When user clicks on "join" or "leave", we run the corresponding mutation
   and update the result of the "user communities" query in the cache. */
const FindCommunityPage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()

  const {
    loading: userCommunitiesLoading,
    error: userCommunitiesError,
    /* As explained, we don't need this data here, we are just ensuring it's
       result is cached so that the `typePolicy` that computes `isMember` can
       pick it up */
  } = useUserCommunitiesQuery({
    variables: { userId },
    fetchPolicy: fetchPolicy('find-communities-user-communities'),
  })

  const [
    lookupCommunities,
    { loading: lookupLoading, error: lookupError, data: lookupData },
  ] = useLookupCommunitiesLazyQuery({
    fetchPolicy: fetchPolicy('find-communities-lookup', ONE_MINUTE_IN_MS),
  })

  const [
    joinCommunity,
    { loading: joinLoading, error: joinError },
  ] = useJoinCommunityMutation({
    update: (cache, { data }) => {
      if (!data) return
      const cacheData = cache.readQuery<
        UserCommunitiesQuery,
        UserCommunitiesQueryVariables
      >({
        query: UserCommunitiesDocument,
        variables: { userId: loggedUserId() },
      })
      if (!cacheData) return
      /* Watch out when crafting new cache data: be sure to include all fields
         and specifically `id`.
         Apollo sometimes warns about `Cache data may be lost when replacing
         the userCommunities field of a Query object.` which it must do because
         it can't tell for sure we didn't messed-up the cache write (its advice
         to define a custome `merge` function for `Query.userCommunities` won't
         work because we don't want following results of the "user communities"
         query to be merged into the previous cached ones). This warning should
         thus be safe to ignore. Also see `assumeImmutableResults`. */
      const newCacheData = {
        ...cacheData,
        userCommunities: [
          ...cacheData.userCommunities,
          data.addUserToCommunity,
        ],
      }
      cache.writeQuery<UserCommunitiesQuery, UserCommunitiesQueryVariables>({
        query: UserCommunitiesDocument,
        variables: { userId: loggedUserId() },
        data: newCacheData,
      })
    },
  })

  const [
    leaveCommunity,
    { loading: leaveLoading, error: leaveError },
  ] = useLeaveCommunityMutation({
    update: (cache, { data }) => {
      if (!data) return
      const cacheData = cache.readQuery<
        UserCommunitiesQuery,
        UserCommunitiesQueryVariables
      >({
        query: UserCommunitiesDocument,
        variables: { userId: loggedUserId() },
      })
      if (!cacheData) return
      const removedCommunityId = data.removeUserFromCommunity
      const newCacheData = {
        ...cacheData,
        userCommunities: cacheData.userCommunities.filter(
          (c) => c.id !== removedCommunityId,
        ),
      }
      cache.writeQuery<UserCommunitiesQuery, UserCommunitiesQueryVariables>({
        query: UserCommunitiesDocument,
        variables: { userId: loggedUserId() },
        data: newCacheData,
      })
    },
  })

  const loading =
    userCommunitiesLoading && lookupLoading && joinLoading && leaveLoading
  const error = userCommunitiesError ?? lookupError ?? joinError ?? leaveError

  return (
    <Layout>
      <Header>Find community</Header>
      <Body>
        {loading && <Spinner />}
        {/* TODO correctly handle error */ error && <>error</>}
        <FindCommunityPanel
          lookedupCommunities={lookupData?.lookupCommunities ?? []}
          lookupCommunities={(name: string) =>
            lookupCommunities({ variables: { name } })
          }
          joinCommunity={(communityId: string) =>
            joinCommunity({ variables: { userId, communityId } })
          }
          leaveCommunity={(communityId: string) =>
            leaveCommunity({ variables: { userId, communityId } })
          }
        />
      </Body>
    </Layout>
  )
}

export default FindCommunityPage
