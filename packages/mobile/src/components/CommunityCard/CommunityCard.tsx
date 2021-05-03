import * as React from 'react'
import { Box, Text, VStack, HStack, Button } from '@chakra-ui/react'
import { CommunityCardFragment as Community } from './community-card.generated'
import { Link } from '@reach/router'
import UserCountChip from '../UserCountChip'
import Heading from '../../atoms/Heading';

interface Props {
  community: Community
  joinCommunity: () => void
  leaveCommunity: () => void
}

export const CommunityCard: React.FC<Props> = (props) => {
  const { community, joinCommunity, leaveCommunity } = props

  return (
    <Box w='100%' h='100%' bg='_thunder' rounded='md' p={ 4 }>
      <VStack spacing={ 2 } align='flex-start'>
        <HStack>
          <Link to={ `/community/${community.id}` }>
            <Heading level={ 3 }>{community.name}</Heading>
          </Link>
          <UserCountChip count={ community.memberCount } />
        </HStack>
        <Text color='white'>{community.address?.street}</Text>
        <Text color='white'>
          {community.address?.zipCode} {community.address?.city}
        </Text>
        <Text color='white'>{community.address?.country}</Text>
        {community.isMember ? (
          <Button colorScheme='red' onClick={ leaveCommunity }>
            Leave
          </Button>
        ) : (
          <Button colorScheme='blue' onClick={ joinCommunity }>
            Join
          </Button>
        )}
      </VStack>
    </Box>
  )
}
