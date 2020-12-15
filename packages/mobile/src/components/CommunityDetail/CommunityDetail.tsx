import * as React from 'react'
import { Box, Heading, Flex } from '@chakra-ui/react'
import { CommunityDetailFragment as Community } from './community-detail.generated'
import SwipeableViews from 'react-swipeable-views'
import { WodCard } from '../WodCard'

interface Props {
  community: Community
}

export const CommunityDetail: React.FC<Props> = (props: Props) => {
  const { community } = props

  return (
    <Box>
      <Flex alignItems='center' mb={4}>
        <Heading fontSize='lg' flex={1}>
          WODs
        </Heading>
      </Flex>
      <Box mx={-6}>
        <SwipeableViews
          style={{ padding: '0 1.5rem' }}
          slideStyle={{ paddingRight: '1rem' }}
          enableMouseEvents
        >
          {community.wods.map((wod) => (
            <WodCard key={wod.id} wod={wod} />
          ))}
        </SwipeableViews>
      </Box>
    </Box>
  )
}
