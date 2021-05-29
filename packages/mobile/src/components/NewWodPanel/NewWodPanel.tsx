import { UserCommunityFragment as Community } from '../../pages/community'
import { UserWorkoutFragment as Workout } from '../../pages/workout/user-workouts.generated'
import { Body } from '../Body'
import { Header } from '../Header'
import { Layout } from '../Layout'
import { NewWod } from '../NewWod'
import { ApolloError } from '@apollo/client'
import { Spinner } from '@chakra-ui/react'
import * as React from 'react'

interface Props {
  loading: boolean
  error: ApolloError | undefined
  selectedCommunityId: string
  communities: Community[] | undefined
  workouts: Workout[] | undefined
  createWod: (communityId: string, workoutId: string, date: string) => void
}

export const NewWodPanel: React.FC<Props> = (props: Props) => {
  const {
    loading,
    error,
    selectedCommunityId,
    communities,
    workouts,
    createWod,
  } = props

  return (
    <Layout>
      <Header>New WOD</Header>
      <Body>
        {loading && <Spinner />}
        {error && <>(error)</>}
        {/* TODO display error if no communities */}
        {communities && communities?.length !== 0 && (
          <NewWod
            initiallySelectedCommunityId={selectedCommunityId}
            communities={communities}
            workouts={workouts}
            createWod={createWod}
          />
        )}
      </Body>
    </Layout>
  )
}
