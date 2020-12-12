import React from 'react'
import { Layout, Header, Body } from '../Layout'
import { WodsPanel } from './WodsPanel'
import { RouteComponentProps } from '@reach/router'
import { Spinner } from '@chakra-ui/react'
import { useUserWodsQuery } from './user-wods.generated'
import { useUserCommunitiesQuery } from './user-communities.generated'
import { loggedUserId } from '../graphql/state/app'

const UserWodsPage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()
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

  return (
    <Layout>
      <Header>My WoD list</Header>
      <Body>
        {(loadingWods || loadingCommunities) && <Spinner />}
        {
          /* TODO properly handle errors */
          (errorWods || errorCommunities) && (
            <p>
              Error {errorWods} {errorCommunities}
            </p>
          )
        }
        {dataWods && dataCommunities && (
          <WodsPanel
            communities={dataCommunities.userCommunities}
            wods={dataWods.userWods}
          />
        )}
      </Body>
    </Layout>
  )
}

export default UserWodsPage
