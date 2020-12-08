import React from 'react'
import { Body, Header, Layout } from '../Layout'
import { Score } from './Score'
import { RouteComponentProps } from '@reach/router'
import { useQuery } from '@apollo/client'
import { GET_WOD } from '../graphql/queries/wod'
import { userId } from '../apollo-client'
import { Spinner } from '@chakra-ui/react'

interface Props extends RouteComponentProps {
  wodId?: string
}

const AddScorePage: React.FC<Props> = (props: Props) => {
  const { wodId } = props

  const { loading, error, data } = useQuery(GET_WOD, {
    fetchPolicy: 'cache-and-network', // FIXME we just want to get the version in cache
    variables: { wodId, userId },
  })

  if (loading) return <Spinner />
  if (error) return <p>Error {error}</p> // TODO

  return (
    <Layout>
      <Header>Add Score</Header>
      <Body>
        <Score wod={data.wod} />
      </Body>
    </Layout>
  )
}

export default AddScorePage
