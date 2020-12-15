import * as React from 'react'
import { RouteComponentProps, useParams } from '@reach/router'
import { useGetCommunityQuery } from './community-detail.generated'
import { CommunityDetailPanel } from '../../../components/CommunityDetailPanel'
import { loggedUserId } from '../../../graphql/state/app'

export const CommunityDetailPage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()
  const props = useParams()
  const { communityId } = props

  const { loading, error, data } = useGetCommunityQuery({
    variables: { communityId, userId },
  })

  return (
    <CommunityDetailPanel
      loading={loading}
      error={error}
      community={data?.community}
    />
  )
}
