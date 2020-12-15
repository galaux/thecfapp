import * as React from 'react'
import {
  Box,
  Flex,
  Text,
  VStack,
  HStack,
  Heading,
  Button,
} from '@chakra-ui/react'
import * as Icon from 'react-feather'
import { CommunityCardFragment as Community } from './community-card.generated'
import { Link } from '@reach/router'

interface Props {
  community: Community
  joinCommunity: () => void
  leaveCommunity: () => void
}

export const CommunityCard: React.FC<Props> = (props) => {
  const { community, joinCommunity, leaveCommunity } = props

  return (
    <Box w='100%' h='100%' bg='_thunder' rounded='md' p={4}>
      <VStack spacing={2} align='flex-start'>
        <HStack>
          <Link to={`/community/${community.id}`}>
            <Heading
              as='h1'
              fontSize='1.25rem'
              fontWeight={400}
              lineHeight={1.2}
              textTransform='uppercase'
              color='_gray'
            >
              {community.name}
            </Heading>
          </Link>
          <Flex>
            <Icon.Users />
            {community.memberCount}
          </Flex>
        </HStack>
        <Text color='white'>{community.address?.street}</Text>
        <Text color='white'>
          {community.address?.zipCode} {community.address?.city}
        </Text>
        <Text color='white'>{community.address?.country}</Text>
        {community.isMember ? (
          <Button colorScheme='red' onClick={leaveCommunity}>
            Leave
          </Button>
        ) : (
          <Button colorScheme='blue' onClick={joinCommunity}>
            Join
          </Button>
        )}
      </VStack>
    </Box>
  )
}
