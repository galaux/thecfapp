import { CommunityDetailPanel } from '../../../components/CommunityDetailPanel'
import { fetchPolicy } from '../../../fetch-policy'
import { useGetCommunityQuery } from './community-detail.generated'
import { RouteComponentProps, useParams } from '@reach/router'
import * as React from 'react'

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
