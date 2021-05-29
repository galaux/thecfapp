import Button from '../../atoms/Button'
import Date from '../../atoms/Date'
import Heading from '../../atoms/Heading'
import Div from '../../common/Div'
import { FontWeights } from '../../styles/theme'
import { Aligns, Directions, Justifys, Size } from '../../types'
import UserCountChip from '../UserCountChip'
import {
  Type,
  WodCardWrapper,
  WorkoutName,
  WorkoutInfo,
  Workout,
  Separator,
} from './WodCard.styled'
import { WodForWodCardFragment as Wod } from './wod-card.generated'
import { navigate } from '@reach/router'
import * as React from 'react'

type WodCardProps = {
  key: string
  wod: Wod
}

const WodCard: React.FC<WodCardProps> = ({ wod }) => {
  // const loggedUserScore = wod.scores?.find((score) => score.belongsToLoggedUser)

  return (
    <WodCardWrapper>
      <Div direction={Directions.column}>
        <Div
          direction={Directions.row}
          align={Aligns.start}
          margin={{ bottom: Size.Xxsmall }}
          justify={Justifys.between}
        >
          <Type>Type</Type>
          {/* For now these are only the user's score while we want the count
          of all submitted scores. Setting the `userId` parameter to
          optional and omitting it in the query should do the trick
          FIXME display the total count of submitted scores */}
          <UserCountChip count={wod.scores.length} />
        </Div>
        <Heading
          level={6}
          weight={FontWeights.Medium}
          margin={{ bottom: Size.Medium }}
        >
          {wod.workout.name}
        </Heading>
        <Workout direction={Directions.column}>
          <WorkoutName>Deadlifts</WorkoutName>
          <WorkoutInfo>
            12 reps
            <Separator />
            70-50 kg
          </WorkoutInfo>
        </Workout>
      </Div>
      <Div
        direction={Directions.row}
        align={Aligns.center}
        justify={Justifys.between}
      >
        <Date wod={wod} />
        <Button onClick={() => navigate(`/scoreboard/${wod.id}`)}>
          See the scoreboard
        </Button>
      </Div>
    </WodCardWrapper>
  )
}

export default WodCard
