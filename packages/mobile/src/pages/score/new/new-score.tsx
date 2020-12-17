import React from 'react'
import { NewScorePanel } from '../../../components/NewScorePanel'
import { RouteComponentProps, useLocation } from '@reach/router'
import { loggedUserId } from '../../../graphql/state/app'
import {
  useCreateScoreMutation,
  WodInputFragment as Wod,
} from './new-score.generated'

export const NewScorePage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()
  const location = useLocation()
  const state = location.state as Wod

  /* TODO Instead of requesting all user scores, this mutation could
     request just the one that got created and write it to the cache */
  const [createScore, { loading, error }] = useCreateScoreMutation()

  return (
    <NewScorePanel
      wod={state}
      createScore={(wodId, value) =>
        createScore({
          variables: {
            userId,
            wodId,
            value,
          },
        })
      }
      createLoading={loading}
      createError={error}
    />
  )
}
