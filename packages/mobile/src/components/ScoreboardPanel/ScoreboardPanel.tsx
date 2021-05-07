import * as React from 'react'
import { Body } from '../Body'
import { Header } from '../Header'
import { Layout } from '../Layout'
import { Spinner, Text, Button, VStack } from '@chakra-ui/react'
import { ApolloError } from '@apollo/client'
import { Scoreboard } from '../Scoreboard'
import { WodForScoreboardPanelFragment as Wod } from './scoreboard-panel.generated'
import { navigate } from '@reach/router'

interface Props {
  loading: boolean
  error: ApolloError | undefined
  wod: Wod | undefined
}

export const ScoreboardPanel: React.FC<Props> = (props: Props) => {
  const { loading, error, wod } = props
  const scores = wod?.scores
  const workout = wod?.workout

  const loggedUserHasScore = !scores?.find((score) => score.belongsToLoggedUser)

  return (
    <Layout>
      <Header>Scoreboard</Header>
      <Body>
        {loading && <Spinner />}
        {error && <>{error}</>}
        {/* TODO display error if no communities */}
        {workout && scores && scores?.length !== 0 && (
          <Scoreboard scores={ scores } workout={ workout } />
        )}
        {scores && scores?.length === 0 && (
          <VStack>
            <Text fontSize='6xl' color='_gray'>
              (·_·)
            </Text>
            <Text>Looks like there is no score yet for this WOD.</Text>
          </VStack>
        )}
        {loggedUserHasScore && (
          <Button
            colorScheme='blue'
            onClick={ () => navigate('/new-score', { state: wod }) }
          >
            Add yours
          </Button>
        )}
      </Body>
    </Layout>
  )
}
