import { UserProfilePanel } from '../../../components/UserProfilePanel'
import { fetchPolicy } from '../../../fetch-policy'
import { extUserToInternalId } from '../../../utils'
import { useGetUserLazyQuery } from './user.generated'
import { Auth0Context } from '@auth0/auth0-react'
import { RouteComponentProps } from '@reach/router'
import { useContext, useEffect } from 'react'

export const UserProfilePage: React.FC<RouteComponentProps> = () => {
  const { user: extUser } = useContext(Auth0Context)
  const loggedUserId = extUserToInternalId(extUser)

  const [getUser, { loading, error, data }] = useGetUserLazyQuery({
    fetchPolicy: fetchPolicy('user-profile-user'),
    variables: { userId: loggedUserId },
  })

  let isMounted = true
  useEffect(() => {
    if (isMounted) {
      getUser()
    }
    return () => {
      // eslint-disable-next-line react-hooks/exhaustive-deps
      isMounted = false
    }
  }, [])

  return <UserProfilePanel loading={loading} error={error} user={data?.user} />
}
