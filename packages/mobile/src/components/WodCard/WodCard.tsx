import * as React from 'react'
import { navigate } from '@reach/router'
import { WodForWodCardFragment as Wod } from './wod-card.generated'
import { Aligns, ButtonVariant, Directions, Justifys, Size } from '../../types';
import { BorderRadius, Colors, FontWeights } from '../../styles/theme';
import { Type, WodCardWrapper, Workout } from './WodCard.styled';
import UserCountChip from '../UserCountChip';
import Div from '../../common/Div';
import Heading from '../../atoms/Heading';
import Date from '../../atoms/Date';
import Button from '../../atoms/Button';


type WodCardProps = {
  key: string
  wod: Wod
}

const WodCard: React.FC<WodCardProps> = ({ wod }) => {
  // const loggedUserScore = wod.scores?.find((score) => score.belongsToLoggedUser)

  return (
    <WodCardWrapper
      pad={ { bottom: Size.Medium, top: Size.Large, horizontal: Size.Medium } }
      backgroundColor={ Colors.gray80 }
      radius={ BorderRadius.Small }
      justify={ Justifys.between }
      direction={ Directions.column }
    >
      <Div direction={ Directions.column }>
        <Div direction={ Directions.row } align={ Aligns.start } margin={ {bottom: Size.Xxsmall} } justify={ Justifys.between }>
          <Type>Type</Type>
          {/* For now these are only the user's score while we want the count
          of all submitted scores. Setting the `userId` parameter to
          optional and omitting it in the query should do the trick
          FIXME display the total count of submitted scores */}
          <UserCountChip count={ wod.scores.length } />
        </Div>
        <Heading level={ 6 } weight={ FontWeights.Medium } margin={ {bottom: Size.Medium} }>{wod.workout.name}</Heading>
        <Workout>{wod.workout.description}</Workout>
      </Div>
      <Div direction={ Directions.row } align={ Aligns.center } justify={ Justifys.between }>
        <Date wod={ wod } />
        <Button
          onClick={ () => navigate(`/scoreboard/${wod.id}`) }
        >
            Scoreboard
        </Button>
      </Div>
    </WodCardWrapper>
  )
}

export default WodCard
