import React from 'react'
import { NewScorePanel } from '../../../components/NewScorePanel'
import { RouteComponentProps, useLocation } from '@reach/router'
import { loggedUserId } from '../../../graphql/state/app'
import {
  useCreateScoreMutation,
  WodInputFragment as Wod,
} from './new-score.generated'
import {
  UserWodsDocument,
  UserWodsQuery,
  UserWodsQueryVariables,
} from '../../wod/user-wods.generated'

export const NewScorePage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()
  const location = useLocation()
  const state = location.state as Wod

  const [createScore, { loading, error }] = useCreateScoreMutation({
    update: (cache, { data }) => {
      if (!data?.createScore) return
      const newScore = data.createScore
      const cacheData = cache.readQuery<UserWodsQuery, UserWodsQueryVariables>({
        query: UserWodsDocument,
        variables: { userId: loggedUserId() },
      })
      const currUserWods = cacheData?.userWods
      const wodForThisScore = currUserWods?.find((wod) => wod.id === state.id)
      if (!wodForThisScore) return
      const newWodForThisScore = {
        ...wodForThisScore,
        scores: [...wodForThisScore.scores, newScore],
      }
      if (!currUserWods) return
      const newUserWods = [
        ...currUserWods.filter((wod) => wod.id !== wodForThisScore.id),
        newWodForThisScore,
      ]
      const newCachedData = {
        ...cacheData,
        userWods: newUserWods,
      }
      cache.writeQuery<UserWodsQuery, UserWodsQueryVariables>({
        query: UserWodsDocument,
        variables: { userId: loggedUserId() },
        data: newCachedData,
      })
    },
  })

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
