import { UserCommunitiesPanel } from '../../../components/UserCommunitiesPanel'
import { extUserToInternalId } from '../../../utils'
import { useUserCommunitiesQuery } from '../user-communities.generated'
import { Auth0Context } from '@auth0/auth0-react'
import { RouteComponentProps } from '@reach/router'
import React, { useContext } from 'react'

export const UserCommunitiesPage: React.FC<RouteComponentProps> = () => {
  const { user: extUser } = useContext(Auth0Context)
  const loggedUserId = extUserToInternalId(extUser)
  const {
    loading: loadingCommunities,
    error: errorCommunities,
    data,
  } = useUserCommunitiesQuery({
    variables: { userId: loggedUserId },
  })

  return (
    <UserCommunitiesPanel
      loading={loadingCommunities}
      error={errorCommunities}
      communities={data?.userCommunities}
    />
  )
}
