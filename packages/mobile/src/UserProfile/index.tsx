import { RouteComponentProps } from '@reach/router'
import { UserProfile } from './UserProfile'
import { Spinner } from '@chakra-ui/react'
import { Body, Header, Layout } from '../Layout'
import { useGetUserQuery } from '../types'

interface Props extends RouteComponentProps {
  userId: string
}

const UserProfilePage: React.FC<Props> = (props) => {
  const { userId } = props

  const { loading, error, data } = useGetUserQuery({
    fetchPolicy: 'cache-and-network',
    variables: { userId },
  })

  if (loading) return <Spinner />
  if (!data || error) return <p>Error: {error} </p> // TODO
  const user = data.user

  return (
    <Layout>
      <Header>My Profile</Header>
      <Body>
        <UserProfile user={user} />
      </Body>
    </Layout>
  )
}

export default UserProfilePage
