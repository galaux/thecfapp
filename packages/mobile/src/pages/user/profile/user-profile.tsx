import { RouteComponentProps } from '@reach/router'
import { UserProfilePanel } from '../../../components/UserProfilePanel'
import { fetchPolicy } from '../../../fetch-policy'
import { loggedUserId } from '../../../graphql/state/app'
import { useGetUserQuery } from './user.generated'

export const UserProfilePage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()

  const { loading, error, data } = useGetUserQuery({
    fetchPolicy: fetchPolicy('user-profile-user'),
    variables: { userId },
  })

  return <UserProfilePanel loading={loading} error={error} user={data?.user} />
}
