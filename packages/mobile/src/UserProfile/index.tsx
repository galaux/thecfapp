import { RouteComponentProps } from '@reach/router'
import { UserProfile } from './UserProfile'
import { Spinner } from '@chakra-ui/react'
import { Body, Header, Layout } from '../Layout'
import { useGetUserQuery } from './user.generated'

interface Props extends RouteComponentProps {
  userId: string
}

const UserProfilePage: React.FC<Props> = (props) => {
  const { userId } = props

  const { loading, error, data } = useGetUserQuery({
    fetchPolicy: 'cache-and-network',
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
