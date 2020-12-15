import * as React from 'react'
import { Body, Header, Layout } from '../../Layout'
import { Spinner } from '@chakra-ui/react'
import { NewCommunityInput } from '../../types'
import { ApolloError } from '@apollo/client'
import { NewCommunity } from '../NewCommunity'
import { NewCommunityFragment as Community } from '../../pages/community/new'
import { navigate } from '@reach/router'

interface Props {
  called: boolean
  loading: boolean
  error: ApolloError | undefined
  community: Community | undefined
  createCommunity: (input: NewCommunityInput) => void
}

export const NewCommunityPanel: React.FC<Props> = (props: Props) => {
  const { called, loading, error, createCommunity, community } = props

  if (community) {
    navigate(`/community/${community.id}`, { state: { community } })
  }

  return (
    <Layout>
      <Header>New Community</Header>
      <Body>
        {called ? (
          <>
            {loading && <Spinner />}
            {error && <>error</>}
          </>
        ) : (
          <NewCommunity createCommunity={createCommunity} />
        )}
      </Body>
    </Layout>
  )
}
