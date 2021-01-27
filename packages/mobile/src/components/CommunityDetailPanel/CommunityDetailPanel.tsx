import * as React from 'react'
import { Flex, HStack, Heading, Text, Box } from '@chakra-ui/react'
import { Layout } from '../Layout'
import { Body } from '../Body'
import { Header } from '../Header'
import { CommunityForCommunityDetailPanelFragment as Community } from './community-detail-panel.generated'
import { ApolloError } from '@apollo/client'
import { oneLineAddress } from '../../utils'
import { UserCountStamp } from '../UserCountStamp'
import { ErrorOr } from '../ErrorOr'
import { LoadingOr } from '../LoadingOr'
import { Link } from '@reach/router'
import SwipeableViews from 'react-swipeable-views'
import { WodCard } from '../WodCard'
import * as Icon from 'react-feather'

interface Props {
  loading: boolean
  error: ApolloError | undefined
  community: Community | undefined
}

export const CommunityDetailPanel: React.FC<Props> = (props: Props) => {
  const { loading, error, community } = props

  return (
    <LoadingOr loading={loading}>
      <ErrorOr error={error}>
        {community && (
          <Layout>
            <Header>
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
                <Text fontSize='0.8rem'>
                  {oneLineAddress(community?.address)}
                </Text>
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
            </Header>
            <Body>
              <Box>
                <Flex alignItems='center' mb={4}>
                  <Heading fontSize='lg' flex={1}>
                    WODs
                  </Heading>
                  <Link to='/new-wod' state={community}>
                    <Flex>
                      New WOD
                      <Icon.PlusCircle />
                    </Flex>
                  </Link>
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
            </Body>
          </Layout>
        )}
      </ErrorOr>
    </LoadingOr>
  )
}
