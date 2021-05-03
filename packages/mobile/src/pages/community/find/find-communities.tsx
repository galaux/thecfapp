import { RouteComponentProps } from '@reach/router'
import { FindCommunityPanel } from '../../../components/FindCommunityPanel'
import { loggedUserId } from '../../../graphql/state/app'
import {
  useJoinCommunityMutation,
  useUserCommunitiesQuery,
  useLeaveCommunityMutation,
} from '../../community'
import { fetchPolicy } from '../../../fetch-policy'
import { useFindCommunitiesLazyQuery } from './find-communities.generated'
import {
  addCommunityToUserCommunitiesCache,
  removeCommunityFromUserCommunitiesCache,
} from '../on-user-communities-mutation'

const ONE_MINUTE_IN_MS = 60 * 1000

/* The logic of this page and its components is as follows:

   This page immediately runs a "user communities" query to ensure its result
   is cached.

   Then, when user does a search, we perform a "find community" query which
   result contains locally computed field `isMember @client`. This field's
   computation – declared as a `typePolicies` at cache instanciation – is
   based on the result of the "user communities".

   When user clicks on "join" or "leave", we run the corresponding mutation
   and update the result of the "user communities" query in the cache. */
export const FindCommunityPage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()

  const {
    loading: userCommunitiesLoading,
    error: userCommunitiesError,
    /* As explained, we don't need this data here, we are just ensuring it's
       result is cached so that the `typePolicy` that computes `isMember` can
       pick it up */
  } = useUserCommunitiesQuery({
    variables: { userId },
    fetchPolicy: fetchPolicy('user-communities'),
  })

  const [
    findCommunities,
    { loading: findLoading, error: findError, data: findData },
  ] = useFindCommunitiesLazyQuery({
    fetchPolicy: fetchPolicy('find-communities-find', ONE_MINUTE_IN_MS),
  })

  const [
    joinCommunity,
    { loading: joinLoading, error: joinError },
  ] = useJoinCommunityMutation({
    update: (cache, { data }) =>
      addCommunityToUserCommunitiesCache(cache, data?.addUserToCommunity),
  })

  const [
    leaveCommunity,
    { loading: leaveLoading, error: leaveError },
  ] = useLeaveCommunityMutation({
    update: (cache, { data }) =>
      removeCommunityFromUserCommunitiesCache(
        cache,
        data?.removeUserFromCommunity,
      ),
  })

  const loading =
    userCommunitiesLoading && findLoading && joinLoading && leaveLoading
  const error = userCommunitiesError ?? findError ?? joinError ?? leaveError

  return (
    <FindCommunityPanel
      loading={ loading }
      error={ error }
      lookedupCommunities={ findData?.findCommunities }
      findCommunities={ (name: string) =>
        findCommunities({ variables: { name } })
      }
      joinCommunity={ (communityId: string) =>
        joinCommunity({ variables: { userId, communityId } })
      }
      leaveCommunity={ (communityId: string) =>
        leaveCommunity({ variables: { userId, communityId } })
      }
    />
  )
}
