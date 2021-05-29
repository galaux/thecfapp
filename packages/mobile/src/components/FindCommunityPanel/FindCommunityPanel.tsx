import { Body } from '../Body'
import { CommunityList } from '../CommunityList'
import { ErrorOr } from '../ErrorOr'
import { Header } from '../Header'
import { Layout } from '../Layout'
import { LoadingOr } from '../LoadingOr'
import { CommunityForFindCommunityPanelFragment as Community } from './find-community-panel.generated'
import { ApolloError } from '@apollo/client'
import {
  Button,
  InputGroup,
  Input,
  InputRightElement,
  Text,
  VStack,
} from '@chakra-ui/react'
import { navigate } from '@reach/router'
import React, { useState } from 'react'
import * as Icon from 'react-feather'

interface Props {
  loading: boolean
  error?: ApolloError
  lookedupCommunities: Community[] | undefined
  findCommunities: (name: string) => void
  joinCommunity: (id: string) => void
}

export const FindCommunityPanel: React.FC<Props> = (props: Props) => {
  const {
    loading,
    error,
    lookedupCommunities,
    findCommunities,
    joinCommunity,
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
        <LoadingOr loading={loading}>
          <ErrorOr error={error}>
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
              >
                {ifEmpty}
              </CommunityList>
            )}
          </ErrorOr>
        </LoadingOr>
      </Body>
    </Layout>
  )
}
