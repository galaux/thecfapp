import * as React from 'react'
import {
  Box,
  Button,
  Flex,
  Heading,
  Text,
  Collapse,
  VStack,
  HStack,
  UnorderedList,
  ListItem,
} from '@chakra-ui/react'
import { navigate } from '@reach/router'
import { WodForWodCardFragment as Wod } from './wod-card.generated'
import * as Icon from 'react-feather'

type Props = {
  key: string
  wod: Wod
}

export const WodCard: React.FC<Props> = (props) => {
  const { wod } = props

  const [show, setShow] = React.useState(false)
  const handleToggle = () => setShow(!show)

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

  function maybeCollapse(text: string) {
    return text.length > 500 ? (
      <>
        <Collapse startingHeight={20} in={show}>
          {text}
        </Collapse>
        <Button size='sm' colorScheme='blue' onClick={handleToggle} mt='1rem'>
          Show {show ? 'Less' : 'More'}
        </Button>
      </>
    ) : (
      <Text>{text}</Text>
    )
  }

  function dateLabel(wod: Wod) {
    const d = new Date(wod.date)
    const month = d.toLocaleString('default', { month: 'short' })
    const day = d.getUTCDate()
    return (
      <Text>
        {month}
        <br />
        <b>{day}</b>
      </Text>
    )
  }

  return (
    <Box w='100%' h='100%' bg='_thunder' rounded='md' p={4}>
      <VStack spacing={2} align='flex-start'>
        <HStack>
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

          <Flex>
            <Icon.Users />
            {
              /* For now these are only the user's score while we want the count
                of all submitted scores. Setting the `userId` parameter to
                optional and omitting it in the query should do the trick
                FIXME display the total count of submitted scores */
              wod.scores.length
            }
          </Flex>
        </HStack>

        {maybeCollapse(wod.workout.description)}

        {wod.scores?.length !== 0 && <Text>Scores:</Text>}
        <UnorderedList pl={6}>
          {wod.scores.map((score) => (
            <ListItem key={score.id}>
              {score.value} {scoringTypeLabel(wod.workout.scoringType)}
            </ListItem>
          ))}
        </UnorderedList>

        {dateLabel(wod)}

        <Button onClick={onAddScoreBtnClick} colorScheme='blue'>
          Add a score
        </Button>
      </VStack>
    </Box>
  )
}
