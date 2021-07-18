import { NewWodPanel } from '../../../components/NewWodPanel'
import { fetchPolicy } from '../../../fetch-policy'
import { extUserToInternalId } from '../../../utils'
import { useUserCommunitiesQuery } from '../../community'
import { UserCommunityFragment as Community } from '../../community'
import { useUserWorkoutsQuery } from '../../workout/user-workouts.generated'
import { useCreateWodMutation } from './new-wod.generated'
import { Auth0Context } from '@auth0/auth0-react'
import { navigate, RouteComponentProps, useLocation } from '@reach/router'
import React, { useContext } from 'react'

export const NewWodPage: React.FC<RouteComponentProps> = () => {
  const location = useLocation()
  const selectedCommunity = location.state as Community

  const { user: extUser } = useContext(Auth0Context)
  const loggedUserId = extUserToInternalId(extUser)

  const {
    loading: communitiesLoading,
    error: communitiesError,
    data: communitiesData,
  } = useUserCommunitiesQuery({
    variables: { userId: loggedUserId },
    fetchPolicy: fetchPolicy('user-communities'),
  })

  const {
    loading: workoutsLoading,
    error: workoutsError,
    data: workoutsData,
  } = useUserWorkoutsQuery({
    fetchPolicy: fetchPolicy('user-workouts'),
  })

  const [createWod, { loading: createWodLoading, error: createWodError }] =
    useCreateWodMutation()

  return (
    <NewWodPanel
      loading={communitiesLoading || workoutsLoading || createWodLoading}
      error={communitiesError ?? workoutsError ?? createWodError}
      selectedCommunityId={selectedCommunity.id}
      communities={communitiesData?.userCommunities}
      workouts={workoutsData?.workouts}
      createWod={(communityId: string, workoutId: string, date: string) => {
        createWod({ variables: { communityId, workoutId, date } })
        navigate(`/community/${communityId}`)
      }}
    />
  )
}
