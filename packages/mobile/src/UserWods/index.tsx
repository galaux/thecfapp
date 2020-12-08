import React from 'react'
import { Layout, Header, Body } from '../Layout'
import { WodsPanel } from './WodsPanel'
import { RouteComponentProps } from '@reach/router'
import { Spinner } from '@chakra-ui/react'
import { useUserWodsQuery, useUserCommunitiesQuery } from '../types'

interface Props extends RouteComponentProps {
  userId: string
}

const UserWodsPage: React.FC<Props> = (props: Props) => {
  const { userId } = props
  const {
    loading: loadingWods,
    error: errorWods,
    data: dataWods,
  } = useUserWodsQuery({
    fetchPolicy: 'cache-and-network',
    variables: { userId },
  })
  const {
    loading: loadingCommunities,
    error: errorCommunities,
    data: dataCommunities,
  } = useUserCommunitiesQuery({
    fetchPolicy: 'cache-and-network',
    variables: { userId },
  })
  if (loadingWods || loadingCommunities) return <Spinner />
  if (!dataWods || errorWods || !dataCommunities || errorCommunities)
    return (
      <p>
        Error {errorWods} {errorCommunities}
      </p>
    ) // TODO properly handle error
  const wods = dataWods.userWods
  const communities = dataCommunities.userCommunities

  return (
    <Layout>
      <Header>My WoD list</Header>
      <Body>
        <WodsPanel communities={communities} wods={wods} />
      </Body>
    </Layout>
  )
}

export default UserWodsPage
