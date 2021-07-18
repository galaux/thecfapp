import { FindCommunityPanel } from '../../../components/FindCommunityPanel'
import { fetchPolicy } from '../../../fetch-policy'
import { extUserToInternalId } from '../../../utils'
import {
  useJoinCommunityMutation,
  useUserCommunitiesQuery,
} from '../../community'
import { addCommunityToUserCommunitiesCache } from '../on-user-communities-mutation'
import { useFindCommunitiesLazyQuery } from './find-communities.generated'
import { Auth0Context } from '@auth0/auth0-react'
import { RouteComponentProps } from '@reach/router'
import { useContext } from 'react'

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
  const { user: extUser } = useContext(Auth0Context)
  const loggedUserId = extUserToInternalId(extUser)

  const {
    loading: userCommunitiesLoading,
    error: userCommunitiesError,
    /* As explained, we don't need this data here, we are just ensuring it's
       result is cached so that the `typePolicy` that computes `isMember` can
       pick it up */
  } = useUserCommunitiesQuery({
    variables: { userId: loggedUserId },
    fetchPolicy: fetchPolicy('user-communities'),
  })

  const [
    findCommunities,
    { loading: findLoading, error: findError, data: findData },
  ] = useFindCommunitiesLazyQuery({
    fetchPolicy: fetchPolicy('find-communities-find', ONE_MINUTE_IN_MS),
  })

  const [joinCommunity, { loading: joinLoading, error: joinError }] =
    useJoinCommunityMutation({
      update: (cache, { data }) =>
        addCommunityToUserCommunitiesCache(
          cache,
          loggedUserId,
          data?.addUserToCommunity,
        ),
    })

  const loading = userCommunitiesLoading && findLoading && joinLoading
  const error = userCommunitiesError ?? findError ?? joinError

  return (
    <FindCommunityPanel
      loading={loading}
      error={error}
      lookedupCommunities={findData?.findCommunities}
      findCommunities={(name: string) =>
        findCommunities({ variables: { name, userId: loggedUserId } })
      }
      joinCommunity={(communityId: string) =>
        joinCommunity({ variables: { userId: loggedUserId, communityId } })
      }
    />
  )
}
