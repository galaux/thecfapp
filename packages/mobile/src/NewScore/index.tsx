import React from 'react'
import { Body, Header, Layout } from '../Layout'
import { NewScore } from './NewScore'
import { RouteComponentProps, useParams } from '@reach/router'
import { Spinner } from '@chakra-ui/react'
import { useGetWodQuery } from './get-wod.generated'
import { loggedUserId } from '../graphql/state/app'

const NewScorePage: React.FC<RouteComponentProps> = () => {
  const { wodId } = useParams()
  const userId = loggedUserId()

  const { loading, error, data } = useGetWodQuery({
    fetchPolicy: 'cache-and-network', // FIXME we just want to get the version in cache
    variables: { wodId, userId },
  })

  return (
    <Layout>
      <Header>Add Score</Header>
      <Body>
        {loading && <Spinner />}
        {
          error && <p>Error {error}</p>
          /* TODO properly handle error */
        }
        {data && <NewScore wod={data.wod} />}
      </Body>
    </Layout>
  )
}

export default NewScorePage
