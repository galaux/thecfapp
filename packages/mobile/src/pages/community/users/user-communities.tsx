import React from 'react'
import { RouteComponentProps } from '@reach/router'
import { UserCommunitiesPanel } from '../../../components/UserCommunitiesPanel'
import { useUserCommunitiesQuery } from '../user-communities.generated'
import { loggedUserId } from '../../../graphql/state/app'
import { removeCommunityFromUserCommunitiesCache } from '../on-user-communities-mutation'
import { useLeaveCommunityMutation } from '../leave-community.generated'

export const UserCommunitiesPage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()
  const {
    loading: loadingCommunities,
    error: errorCommunities,
    data,
  } = useUserCommunitiesQuery({
    variables: { userId },
  })

  const [leaveCommunity, { error: leaveError }] = useLeaveCommunityMutation({
    update: (cache, { data }) =>
      removeCommunityFromUserCommunitiesCache(
        cache,
        data?.removeUserFromCommunity,
      ),
  })

  return (
    <UserCommunitiesPanel
      loading={loadingCommunities}
      error={errorCommunities ?? leaveError}
      communities={data?.userCommunities}
      leaveCommunity={(communityId: string) =>
        leaveCommunity({ variables: { userId, communityId } })
      }
    />
  )
}
