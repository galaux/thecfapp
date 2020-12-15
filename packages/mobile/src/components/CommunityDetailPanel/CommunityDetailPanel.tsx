import * as React from 'react'
import { Spinner } from '@chakra-ui/react'
import { Body, Header, Layout } from '../../Layout'
import { CommunityDetailFragment as Community } from '../CommunityDetail'
import { ApolloError } from '@apollo/client'
import { CommunityDetail } from '../CommunityDetail'

interface Props {
  loading: boolean
  error: ApolloError | undefined
  community: Community | undefined
}

export const CommunityDetailPanel: React.FC<Props> = (props: Props) => {
  const { loading, error, community } = props

  return (
    <Layout>
      <Header>Community</Header>
      <Body>
        {loading && <Spinner />}
        {error && <>error</>}
        {community && <CommunityDetail community={community} />}
      </Body>
    </Layout>
  )
}
