import * as React from 'react'
import { Text, Spinner, Button, VStack } from '@chakra-ui/react'
import { Body } from '../Body'
import { Header } from '../Header'
import { Layout } from '../Layout'
import { ApolloError } from '@apollo/client'
import { CommunityList } from '../CommunityList'
import { navigate } from '@reach/router'
import { CommunityCardFragment as CommunityCard } from '../CommunityCard'
import { UserCommunityFragment as UserCommunity } from '../../pages/community'

type Props = {
  loading: boolean
  error: ApolloError | undefined
  communities: UserCommunity[] | undefined
}

/**
 * Turn a `UserCommunity` into a `CommunityCard` by adding missing field
 * `isMember` which is always true on a _user_ community.
 */
function userCommunityToCommunityCard(c: UserCommunity): CommunityCard {
  return {
    ...c,
    isMember: true,
  } as CommunityCard
}

export const UserCommunitiesPanel: React.FC<Props> = (props) => {
  const { loading, error, communities } = props

  const noop = () => null

  const ifEmpty = (
    <VStack>
      <Text fontSize='6xl' color='_gray'>
        (·_·)
      </Text>
      <Text>Looks like you don't belong to any community … yet.</Text>
      <Button colorScheme='blue' onClick={ () => navigate('/find-community') }>
        Find a community
      </Button>
    </VStack>
  )

  return (
    <Layout>
      <Header>User communities</Header>
      <Body>
        {loading && <Spinner />}
        {(error || !communities) && <p>Error {error}</p>}
        {communities && (
          <CommunityList
            communities={ communities.map(userCommunityToCommunityCard) }
            joinCommunity={ noop }
          >
            {ifEmpty}
          </CommunityList>
        )}
      </Body>
    </Layout>
  )
}
