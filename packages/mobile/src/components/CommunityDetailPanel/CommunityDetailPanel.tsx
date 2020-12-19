import * as React from 'react'
import { Spinner, Flex, HStack, Heading, Text } from '@chakra-ui/react'
import { Layout } from '../Layout'
import { Body } from '../Body'
import { Header } from '../Header'
import { CommunityDetailPanelFragment as Community } from './community-detail-panel.generated'
import { ApolloError } from '@apollo/client'
import { CommunityDetail } from '../CommunityDetail'
import { oneLineAddress } from '../../utils'
import { UserCountStamp } from '../UserCountStamp'

interface Props {
  loading: boolean
  error: ApolloError | undefined
  community: Community | undefined
}

export const CommunityDetailPanel: React.FC<Props> = (props: Props) => {
  const { loading, error, community } = props

  return (
    <Layout>
      <Header>
        {community && (
          <>
            <Heading
              as='h1'
              fontSize='1.25rem'
              fontWeight={400}
              lineHeight={1.2}
              textTransform='uppercase'
            >
              {community.name}
            </Heading>
            <Text fontSize='0.8rem'>{oneLineAddress(community?.address)}</Text>
            <HStack>
              <Flex>
                <Text fontSize='1.4rem'>{community.wods.length}</Text>{' '}
                <Text fontSize='0.8rem'>
                  WOD
                  <br />
                  PERFORMED
                </Text>
              </Flex>
              <UserCountStamp count={community.memberCount} />
            </HStack>
          </>
        )}
      </Header>
      <Body>
        {loading && <Spinner />}
        {error && <>error</>}
        {community && <CommunityDetail community={community} />}
      </Body>
    </Layout>
  )
}
