import React from 'react'
import { RouteComponentProps } from '@reach/router'
import { UserCommunitiesPanel } from '../../../components/UserCommunitiesPanel'
import { useUserCommunitiesQuery } from '../user-communities.generated'
import { loggedUserId } from '../../../graphql/state/app'

export const UserCommunitiesPage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()
  const {
    loading: loadingCommunities,
    error: errorCommunities,
    data,
  } = useUserCommunitiesQuery({
    variables: { userId },
  })

  return (
    <UserCommunitiesPanel
      loading={ loadingCommunities }
      error={ errorCommunities }
      communities={ data?.userCommunities }
    />
  )
}
