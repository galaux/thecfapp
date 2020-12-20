import { UserProfilePanelFragment as User } from './user-profile-panel.generated'
import { Text } from '@chakra-ui/react'
import { Spinner } from '@chakra-ui/react'
import { Body } from '../Body'
import { Header } from '../Header'
import { Layout } from '../Layout'
import { ApolloError } from '@apollo/client'

interface Props {
  loading: boolean
  error: ApolloError | undefined
  user: User | undefined
}

export const UserProfilePanel: React.FC<Props> = (props) => {
  const { loading, error, user } = props

  return (
    <Layout>
      <Header>My Profile</Header>
      <Body>
        {loading && <Spinner />}
        {error && <p>Error: {error} </p> /* TODO properly handle error */}
        {user && (
          <Text>
            ID: {user.id}
            <br />
            Name: {user.name}
          </Text>
        )}
      </Body>
    </Layout>
  )
}
