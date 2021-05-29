import { scoringTypeLabel, sortScores } from '../../utils'
import {
  ScoreboardScoreFragment as Score,
  WorkoutForScoreboardFragment as Workout,
} from './scoreboard.generated'
import { Text, OrderedList, ListItem } from '@chakra-ui/react'
import React from 'react'

interface Props {
  scores: Score[]
  workout: Workout
}

export const Scoreboard: React.FC<Props> = (props: Props) => {
  const { scores, workout } = props
  const scoringType = scoringTypeLabel(workout.scoringType)

  return (
    <>
      <Text>Scoreboard</Text>
      <OrderedList>
        {sortScores(workout.scoringType, scores).map((score) => (
          <ListItem key={score.id}>
            <Text
              as={score.belongsToLoggedUser ? 'b' : undefined}
            >{`${score.user.name}: ${score.value} ${scoringType}`}</Text>
          </ListItem>
        ))}
      </OrderedList>
    </>
  )
}
