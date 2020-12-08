import React, { useState } from 'react'
import { WodList } from './WodList'
import { UserCommunitiesCommunityFragment, UserWodsWodFragment } from '../types'
import { Box, Flex, Select, Heading } from '@chakra-ui/react'

interface Props {
  communities: UserCommunitiesCommunityFragment[]
  wods: UserWodsWodFragment[]
}

export const WodsPanel: React.FC<Props> = (props) => {
  const { communities, wods } = props

  const [selectedCommunityId, setSelectedCommunityId] = useState<string | null>(
    null,
  )

  const handleSelectCommunityChange = (communityId: string | null) => {
    setSelectedCommunityId(communityId)
  }

  return (
    <Box>
      <Flex alignItems='center' mb={4}>
        <Heading fontSize='lg' flex={1}>
          WOD
        </Heading>
        <Select
          w='auto'
          border={0}
          color='_gray'
          fontSize='md'
          placeholder='Select box …'
          selected={selectedCommunityId}
          onChange={(event) =>
            handleSelectCommunityChange(
              event.target.value === '' ? null : event.target.value,
            )
          }
        >
          {communities.map((community) => (
            <option key={community.id} value={community.id}>
              {community.name}
            </option>
          ))}
        </Select>
      </Flex>
      <Box mx={-6}>
        <WodList selectedCommunityId={selectedCommunityId} wods={wods} />
      </Box>
    </Box>
  )
}
