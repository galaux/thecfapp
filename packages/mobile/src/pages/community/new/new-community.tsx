import { NewCommunityPanel } from '../../../components/NewCommunityPanel'
import { NewCommunityInput } from '../../../types'
import { extUserToInternalId } from '../../../utils'
import { addCommunityToUserCommunitiesCache } from '../on-user-communities-mutation'
import { useCreateCommunityMutation } from './new-community.generated'
import { Auth0Context } from '@auth0/auth0-react'
import { RouteComponentProps } from '@reach/router'
import React, { useContext } from 'react'

export const NewCommunityPage: React.FC<RouteComponentProps> = () => {
  const { user: extUser } = useContext(Auth0Context)
  const loggedUserId = extUserToInternalId(extUser)

  const [createCommunityRaw, { loading, error, data, called }] =
    useCreateCommunityMutation({
      update: (cache, { data }) =>
        addCommunityToUserCommunitiesCache(
          cache,
          loggedUserId,
          data?.createCommunity,
        ),
    })

  const createCommunity = (input: NewCommunityInput) => {
    createCommunityRaw({
      variables: { creatorId: loggedUserId, input },
    })
  }

  return (
    <NewCommunityPanel
      called={called}
      loading={loading}
      error={error}
      community={data?.createCommunity}
      createCommunity={createCommunity}
    />
  )
}
