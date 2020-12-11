import * as React from 'react'
import {
  Box,
  Button,
  Heading,
  Text,
  VStack,
  UnorderedList,
  ListItem,
} from '@chakra-ui/react'
import { navigate } from '@reach/router'
import { UserWodsWodFragment as Wod } from './user-wods.generated'

type Props = {
  key: string
  wod: Wod
}

export const WodListItem: React.FC<Props> = (props) => {
  const { wod } = props

  const onAddScoreBtnClick = () => navigate(`/add-score/${wod.id}`)

  // TODO use enum taken from GraphQL types
  const scoringTypeLabel = (type: string) => {
    switch (type) {
      case 'REPETITIONS': {
        return 'repetitions'
      }
      case 'DURATION_MS': {
        return 'ms'
      }
    }
  }

  return (
    <Box w='100%' h='100%' bg='_thunder' rounded='md' p={4}>
      <VStack spacing={2} align='flex-start'>
        <Heading
          as='h1'
          fontSize='1.25rem'
          fontWeight={400}
          lineHeight={1.2}
          textTransform='uppercase'
          color='_gray'
        >
          {wod.workout.name}
        </Heading>
        <Text>{wod.workout.description}</Text>

        {wod.scores?.length !== 0 && <Text>Scores:</Text>}
        <UnorderedList pl={6}>
          {wod.scores.map((score) => (
            <ListItem key={score.id}>
              {score.value} {scoringTypeLabel(wod.workout.scoringType)}
            </ListItem>
          ))}
        </UnorderedList>

        <Button onClick={onAddScoreBtnClick} colorScheme='blue'>
          Add a score
        </Button>
      </VStack>
    </Box>
  )
}
