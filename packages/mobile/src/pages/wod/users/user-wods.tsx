import React from 'react'
import { UserWodsPanel } from '../../../components/UserWodsPanel'
import { RouteComponentProps } from '@reach/router'
import { fetchPolicy } from '../../../fetch-policy'
import { loggedUserId } from '../../../graphql/state/app'
import { useUserWodsQuery } from '../user-wods.generated'

export const UserWodsPage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()
  const { loading, error, data } = useUserWodsQuery({
    fetchPolicy: fetchPolicy('user-wods-wods'),
    variables: { userId },
  })

  return <UserWodsPanel loading={loading} error={error} wods={data?.userWods} />
}
