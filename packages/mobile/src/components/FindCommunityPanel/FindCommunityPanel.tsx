import React, { useState } from 'react'
import {
  Button,
  Stack,
  InputGroup,
  Input,
  InputRightElement,
  Text,
  VStack,
  Spinner,
} from '@chakra-ui/react'
import { Body } from '../Body'
import { Header } from '../Header'
import { Layout } from '../Layout'
import * as Icon from 'react-feather'
import { CommunityList } from '../CommunityList'
import { FoundCommunityFragment as Community } from '../../pages/community'
import { ApolloError } from '@apollo/client'
import { navigate } from '@reach/router'

interface Props {
  loading: boolean
  error?: ApolloError
  lookedupCommunities: Community[] | undefined
  findCommunities: (name: string) => void
  joinCommunity: (id: string) => void
  leaveCommunity: (id: string) => void
}

export const FindCommunityPanel: React.FC<Props> = (props: Props) => {
  const {
    loading,
    error,
    lookedupCommunities,
    findCommunities,
    joinCommunity,
    leaveCommunity,
  } = props
  const [name, setName] = useState<string | null>(null)

  const onClickFind = () => {
    if (name) findCommunities(name)
  }

  const ifEmpty = (
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

  return (
    <Layout>
      <Header>Find community</Header>
      <Body>
        {loading && <Spinner />}
        {error && <>error</>}
        <Stack spacing={4}>
          <InputGroup>
            <Input
              placeholder='Search a community'
              onChange={(e) => setName(e.target.value)}
            />
            <InputRightElement>
              <Button h='1.75rem' size='sm' onClick={onClickFind}>
                <Icon.Search />
              </Button>
            </InputRightElement>
          </InputGroup>
          {lookedupCommunities && (
            <CommunityList
              communities={lookedupCommunities}
              joinCommunity={joinCommunity}
              leaveCommunity={leaveCommunity}
            >
              {ifEmpty}
            </CommunityList>
          )}
        </Stack>
      </Body>
    </Layout>
  )
}
