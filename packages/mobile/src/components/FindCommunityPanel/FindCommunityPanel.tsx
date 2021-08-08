import Button from '../../atoms/Button'
import Label from '../../atoms/Label'
import Div from '../../common/Div'
import InputGroup from '../../common/InputGroup'
import Input from '../../molecules/Input'
import { ButtonType, Size } from '../../types'
import { Body } from '../Body'
import { CommunityList } from '../CommunityList'
import { ErrorOr } from '../ErrorOr'
import { Header } from '../Header'
import { Layout } from '../Layout'
import { LoadingOr } from '../LoadingOr'
import { CommunityForFindCommunityPanelFragment as Community } from './find-community-panel.generated'
import { ApolloError } from '@apollo/client'
import { Text, VStack } from '@chakra-ui/react'
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
  const [name, setName] = useState('')

  const onClickFind = () => {
    if (name) findCommunities(name)
  }

  const searchOnChange = (inputValue: string) => {
    setName(inputValue)
  }

  const ifEmpty = (
    <VStack>
      <Text fontSize='6xl' color='_gray'>
        (·_·)
      </Text>
      <Text>Looks like this community doesn't exist yet.</Text>
      <Button onClick={() => navigate('/new-community')}>
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
            <Div margin={{ bottom: Size.Xlarge }}>
              <Label htmlFor='search'>Search a box</Label>
              <InputGroup>
                <Input id='search' value={name} onChange={searchOnChange} />
                <Button type={ButtonType.button} onClick={onClickFind}>
                  <Icon.Search size={'14'} />
                </Button>
              </InputGroup>
            </Div>
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
