import * as React from 'react'
import { RouteComponentProps, useParams } from '@reach/router'
import { useGetCommunityQuery } from './community-detail.generated'
import { CommunityDetailPanel } from '../../../components/CommunityDetailPanel'
import { fetchPolicy } from '../../../fetch-policy'

export const CommunityDetailPage: React.FC<RouteComponentProps> = () => {
  const props = useParams()
  const { communityId } = props

  const { loading, error, data } = useGetCommunityQuery({
    variables: { communityId },
    fetchPolicy: fetchPolicy('get-community'),
  })

  return (
    <CommunityDetailPanel
      loading={loading}
      error={error}
      community={data?.community}
    />
  )
}
