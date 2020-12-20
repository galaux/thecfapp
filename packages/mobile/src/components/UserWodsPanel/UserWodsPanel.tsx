import React, { useState } from 'react'
import { WodSwipeList } from '../WodSwipeList'
import { UserWodPanelFragment as Wod } from './user-wod-panel.generated'
import { Box, Flex, Select, Heading } from '@chakra-ui/react'
import { Body } from '../Body'
import { Header } from '../Header'
import { Layout } from '../Layout'
import { uniqBy } from 'lodash/fp'
import { ApolloError } from '@apollo/client'
import { ErrorOr } from '../ErrorOr'
import { LoadingOr } from '../LoadingOr'

interface Props {
  loading: boolean
  error: ApolloError | undefined
  wods: Wod[] | undefined
}

export const UserWodsPanel: React.FC<Props> = (props) => {
  const { loading, error, wods } = props

  const [selectedCommunityId, setSelectedCommunityId] = useState<string | null>(
    null,
  )

  const communities = uniqBy(
    'id',
    wods?.map((wod) => wod.community),
  )

  const handleSelectCommunityChange = (communityId: string | null) => {
    setSelectedCommunityId(communityId)
  }

  return (
    <Layout>
      <Header>My WoD list</Header>
      <Body>
        <LoadingOr loading={loading}>
          <ErrorOr error={error}>
            {wods && (
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
                    selected={selectedCommunityId} // FIXME or `value`?
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
                  <WodSwipeList
                    selectedCommunityId={selectedCommunityId}
                    wods={wods}
                  />
                </Box>
              </Box>
            )}
          </ErrorOr>
        </LoadingOr>
      </Body>
    </Layout>
  )
}
