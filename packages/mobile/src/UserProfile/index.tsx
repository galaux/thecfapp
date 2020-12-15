import { RouteComponentProps } from '@reach/router'
import { UserProfile } from './UserProfile'
import { Spinner } from '@chakra-ui/react'
import { Body, Header, Layout } from '../Layout'
import { useGetUserQuery } from './user.generated'
import { loggedUserId } from '../graphql/state/app'
import { fetchPolicy } from '../fetch-policy'

const UserProfilePage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()

  const { loading, error, data } = useGetUserQuery({
    fetchPolicy: fetchPolicy('user-profile-user'),
    variables: { userId },
  })

  return (
    <Layout>
      <Header>My Profile</Header>
      <Body>
        {loading && <Spinner />}
        {error && <p>Error: {error} </p> /* TODO properly handle error */}
        {data && <UserProfile user={data.user} />}
      </Body>
    </Layout>
  )
}

export default UserProfilePage
