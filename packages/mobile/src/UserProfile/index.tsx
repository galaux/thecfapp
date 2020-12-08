import { RouteComponentProps } from '@reach/router'
import { useQuery } from '@apollo/client'
import { UserProfile } from './UserProfile'
import { Spinner } from '@chakra-ui/react'
import { GET_USER } from '../graphql/queries/user'
import { Body, Header, Layout } from '../Layout'

interface Props extends RouteComponentProps {
  userId: string
}

const UserProfilePage: React.FC<Props> = (props) => {
  const { userId } = props

  const { loading, error, data } = useQuery(GET_USER, {
    fetchPolicy: 'cache-and-network',
    variables: { userId },
  })

  if (loading) return <Spinner />
  if (error) return <p>Error: {error} </p> // TODO
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
