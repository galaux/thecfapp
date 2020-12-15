import React, { useState } from 'react'
import {
  Button,
  Stack,
  InputGroup,
  Input,
  InputRightElement,
} from '@chakra-ui/react'
import * as Icon from 'react-feather'
import { CommunityCard } from './CommunityCard'
import { CommunityForLookupCommunitiesFragment as Community } from './find-community.generated'

interface Props {
  lookedupCommunities: Community[]
  lookupCommunities: (name: string) => void
  joinCommunity: (id: string) => void
  leaveCommunity: (id: string) => void
}

export const FindCommunityPanel: React.FC<Props> = (props: Props) => {
  const {
    lookedupCommunities,
    lookupCommunities,
    joinCommunity,
    leaveCommunity,
  } = props
  const [name, setName] = useState<string | null>(null)

  const onClickLookup = () => {
    if (name) lookupCommunities(name)
  }

  return (
    <Stack spacing={4}>
      <InputGroup>
        <Input
          placeholder='Search a community'
          onChange={(e) => setName(e.target.value)}
        />
        <InputRightElement>
          <Button h='1.75rem' size='sm' onClick={onClickLookup}>
            <Icon.Search />
          </Button>
        </InputRightElement>
      </InputGroup>

      {lookedupCommunities.map((community) => (
        <CommunityCard
          key={community.id}
          community={community}
          joinCommunity={() => joinCommunity(community.id)}
          leaveCommunity={() => leaveCommunity(community.id)}
        />
      ))}
    </Stack>
  )
}
