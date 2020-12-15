import React from 'react'
import { Body, Header, Layout } from '../Layout'
import { NewScore } from './NewScore'
import { RouteComponentProps, useParams } from '@reach/router'
import { Spinner } from '@chakra-ui/react'
import { useGetWodQuery } from './get-wod.generated'
import { loggedUserId } from '../graphql/state/app'
import { fetchPolicy } from '../fetch-policy'
import { useCreateScoreMutation } from './new-score.generated'

const NewScorePage: React.FC<RouteComponentProps> = () => {
  const { wodId } = useParams()
  const userId = loggedUserId()

  // TODO this `NewScore` could just work on a `wod` passed by the previous page
  const { loading, error, data } = useGetWodQuery({
    fetchPolicy: fetchPolicy('new-score-wod'),
    variables: { wodId, userId },
  })

  /* TODO Instead of requesting all user scores, this mutation could
     request just the one that got created and write it to the cache */
  const mutationTupleCreateScore = useCreateScoreMutation()

  return (
    <Layout>
      <Header>Add Score</Header>
      <Body>
        {loading && <Spinner />}
        {
          error && <p>Error {error}</p>
          /* TODO properly handle error */
        }
        {data && (
          <NewScore
            wod={data.wod}
            mutationTupleCreateScore={mutationTupleCreateScore}
          />
        )}
      </Body>
    </Layout>
  )
}

export default NewScorePage
