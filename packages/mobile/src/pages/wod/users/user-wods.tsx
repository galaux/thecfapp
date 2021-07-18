import { UserWodsPanel } from '../../../components/UserWodsPanel'
import { fetchPolicy } from '../../../fetch-policy'
import { extUserToInternalId } from '../../../utils'
import { useUserWodsQuery } from '../user-wods.generated'
import { Auth0Context } from '@auth0/auth0-react'
import { RouteComponentProps } from '@reach/router'
import React, { useContext } from 'react'

export const UserWodsPage: React.FC<RouteComponentProps> = () => {
  const { user: extUser } = useContext(Auth0Context)
  const loggedUserId = extUserToInternalId(extUser)
  const { loading, error, data } = useUserWodsQuery({
    fetchPolicy: fetchPolicy('user-wods-wods'),
    variables: { userId: loggedUserId },
  })

  return <UserWodsPanel loading={loading} error={error} wods={data?.userWods} />
}
