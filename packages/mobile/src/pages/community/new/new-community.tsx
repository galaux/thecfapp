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

  /*
     const community = {
     __typename: 'Community',
     name: input.name,
     address: {
     __typename: 'Address',
     city: input.city,
     country: input.country,
     street: input.street,
     zipCode: input.zipCode,
     },
     } as Community // TODO have `nonOptionalTypename: true`?
     createCommunityRaw({
     variables: { creatorId: loggedUserId(), input },
     optimisticResponse: {
     __typename: 'Mutation',
     createCommunity: community,
     },
     })
   */

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
