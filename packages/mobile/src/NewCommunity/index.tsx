import React from 'react'
import { RouteComponentProps } from '@reach/router'
import { NewCommunity } from './NewCommunity'
import { Body, Header, Layout } from '../Layout'
import { useCreateCommunityMutation } from './new-community.generated'
import { NewCommunityInput } from '../types'
import { Spinner } from '@chakra-ui/react'
import { loggedUserId } from '../graphql/state/app'

const NewCommunityPage: React.FC<RouteComponentProps> = () => {
  /* FIXME needs to add the new community to the cached result of userCommunities
     This would tend to show queries such a `userCommunities` are somewhat _global_
     and not _per component_!
  */
  const [createCommunity, { loading, error }] = useCreateCommunityMutation()

  return (
    <Layout>
      <Header>New Community</Header>
      <Body>
        {loading && <Spinner />}
        {error && <>error</>}
        <NewCommunity
          createCommunity={(input: NewCommunityInput) =>
            createCommunity({ variables: { creatorId: loggedUserId(), input } })
          }
        />
      </Body>
    </Layout>
  )
}

export default NewCommunityPage
