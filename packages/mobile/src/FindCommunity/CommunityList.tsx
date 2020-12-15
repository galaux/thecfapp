import * as React from 'react'
import { Text, VStack, Button } from '@chakra-ui/react'
import { CommunityForLookupCommunitiesFragment as Community } from './find-community.generated'
import { CommunityCard } from './CommunityCard'
import { navigate } from '@reach/router'

interface Props {
  communities: Community[]
  joinCommunity: (id: string) => void
  leaveCommunity: (id: string) => void
}

export const CommunityList: React.FC<Props> = (props: Props) => {
  const { communities, joinCommunity, leaveCommunity } = props

  return communities.length !== 0 ? (
    <>
      {communities.map((community) => (
        <CommunityCard
          key={community.id}
          community={community}
          joinCommunity={() => joinCommunity(community.id)}
          leaveCommunity={() => leaveCommunity(community.id)}
        />
      ))}
    </>
  ) : (
    <VStack>
      <Text fontSize='6xl' color='_gray'>
        (·_·)
      </Text>
      <Text>Looks like this community doesn't exist yet.</Text>
      <Button colorScheme='blue' onClick={() => navigate('/new-community')}>
        Create your community
      </Button>
    </VStack>
  )
}
