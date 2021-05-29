import { UserProfilePanel } from '../../../components/UserProfilePanel'
import { fetchPolicy } from '../../../fetch-policy'
import { loggedUserId } from '../../../graphql/state/app'
import { useGetUserLazyQuery } from './user.generated'
import { RouteComponentProps } from '@reach/router'
import { useEffect } from 'react'

export const UserProfilePage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()

  const [getUser, { loading, error, data }] = useGetUserLazyQuery({
    fetchPolicy: fetchPolicy('user-profile-user'),
    variables: { userId },
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
