import React from 'react'
import { RouteComponentProps } from '@reach/router'
import { NewCommunityPanel } from '../../../components/NewCommunityPanel'
import { useCreateCommunityMutation } from './new-community.generated'
import { loggedUserId } from '../../../graphql/state/app'
import { NewCommunityInput } from '../../../types'
import { addCommunityToUserCommunitiesCache } from '../on-user-communities-mutation'

export const NewCommunityPage: React.FC<RouteComponentProps> = () => {
  const [
    createCommunityRaw,
    { loading, error, data, called },
  ] = useCreateCommunityMutation({
    update: (cache, { data }) =>
      addCommunityToUserCommunitiesCache(cache, data?.createCommunity),
  })

  const createCommunity = (input: NewCommunityInput) => {
    createCommunityRaw({
      variables: { creatorId: loggedUserId(), input },
    })
  }

  return (
    <NewCommunityPanel
      called={ called }
      loading={ loading }
      error={ error }
      community={ data?.createCommunity }
      createCommunity={ createCommunity }
    />
  )
}
