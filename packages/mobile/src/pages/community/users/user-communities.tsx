import { UserCommunitiesPanel } from '../../../components/UserCommunitiesPanel'
import { loggedUserId } from '../../../graphql/state/app'
import { useUserCommunitiesQuery } from '../user-communities.generated'
import { RouteComponentProps } from '@reach/router'
import React from 'react'

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
      loading={loadingCommunities}
      error={errorCommunities}
      communities={data?.userCommunities}
    />
  )
}
