import { NewScorePanel } from '../../../components/NewScorePanel'
import { loggedUserId } from '../../../graphql/state/app'
import { Score } from '../../../types'
import {
  UserWodsDocument,
  UserWodsQuery,
  UserWodsQueryVariables,
} from '../../wod/user-wods.generated'
import { NewScoreFragment, useCreateScoreMutation } from './new-score.generated'
import { WodInputFragment as Wod } from './wod-input.generated'
import { navigate, RouteComponentProps, useLocation } from '@reach/router'
import React from 'react'

export const NewScorePage: React.FC<RouteComponentProps> = () => {
  const userId = loggedUserId()
  const location = useLocation()
  const state = location.state as Wod

  const [createScoreRaw, { loading, error }] = useCreateScoreMutation({
    update: (cache, { data }) => {
      if (!data?.createScore) return
      const newScore = {
        ...data.createScore,
        belongsToLoggedUser: true,
      } as Score
      const cacheData = cache.readQuery<UserWodsQuery, UserWodsQueryVariables>({
        query: UserWodsDocument,
        variables: { userId: loggedUserId() },
      })
      const currUserWods = cacheData?.userWods
      const wodForThisScore = currUserWods?.find((wod) => wod?.id === state.id)
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

  function createScore(wodId: string, value: number) {
    createScoreRaw({
      variables: {
        userId,
        wodId,
        value,
      },
      optimisticResponse: {
        __typename: 'Mutation',
        createScore: {
          __typename: 'Score',
          value,
        } as NewScoreFragment,
      },
    })
    navigate('/wod-list')
  }

  return (
    <NewScorePanel
      wod={state}
      createScore={createScore}
      createLoading={loading}
      createError={error}
    />
  )
}
