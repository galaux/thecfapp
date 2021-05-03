import React from 'react'
import { navigate, RouteComponentProps, useLocation } from '@reach/router'
import { NewWodPanel } from '../../../components/NewWodPanel'
import { useUserCommunitiesQuery } from '../../community'
import { loggedUserId } from '../../../graphql/state/app'
import { fetchPolicy } from '../../../fetch-policy'
import { useUserWorkoutsQuery } from '../../workout/user-workouts.generated'
import { UserCommunityFragment as Community } from '../../community'
import { useCreateWodMutation } from './new-wod.generated'

export const NewWodPage: React.FC<RouteComponentProps> = () => {
  const location = useLocation()
  const selectedCommunity = location.state as Community

  const userId = loggedUserId()

  const {
    loading: communitiesLoading,
    error: communitiesError,
    data: communitiesData,
  } = useUserCommunitiesQuery({
    variables: { userId },
    fetchPolicy: fetchPolicy('user-communities'),
  })

  const {
    loading: workoutsLoading,
    error: workoutsError,
    data: workoutsData,
  } = useUserWorkoutsQuery({
    fetchPolicy: fetchPolicy('user-workouts'),
  })

  const [
    createWod,
    { loading: createWodLoading, error: createWodError },
  ] = useCreateWodMutation()

  return (
    <NewWodPanel
      loading={ communitiesLoading || workoutsLoading || createWodLoading }
      error={ communitiesError ?? workoutsError ?? createWodError }
      selectedCommunityId={ selectedCommunity.id }
      communities={ communitiesData?.userCommunities }
      workouts={ workoutsData?.workouts }
      createWod={ (communityId: string, workoutId: string, date: string) => {
        createWod({ variables: { communityId, workoutId, date } })
        navigate(`/community/${communityId}`)
      } }
    />
  )
}
