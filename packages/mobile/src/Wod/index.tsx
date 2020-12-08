import React from 'react'
import { useQuery } from '@apollo/client'
import { Layout, Header, Body } from '../Layout'
import { WodPanel } from './WodPanel'
import { ICommunity } from '../types'
import { RouteComponentProps } from '@reach/router'
import { Spinner } from '@chakra-ui/react'
import { GET_USER } from '../graphql/queries/user'

interface Props extends RouteComponentProps {
  userId: string
}

const WodListPage: React.FC<Props> = (props: Props) => {
  const { userId } = props

  const { loading, error, data } = useQuery(GET_USER, {
    fetchPolicy: 'cache-and-network',
    variables: { userId },
  })
  if (loading) return <Spinner />
  if (error) return <p>Error {error}</p> // TODO
  const wods = [].concat(
    ...data.user.communities.map((community: ICommunity) => community.wods),
  )
  const communities = data.user.communities

  return (
    <Layout>
      <Header>My WoD list</Header>
      <Body>
        <WodPanel communities={communities} wods={wods} />
      </Body>
    </Layout>
  )
}

export default WodListPage
