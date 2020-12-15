import React, { FormEvent, useState } from 'react'
import {
  Box,
  Button,
  Heading,
  Text,
  VStack,
  NumberInput,
  NumberInputField,
  NumberInputStepper,
  NumberIncrementStepper,
  NumberDecrementStepper,
  Spinner,
} from '@chakra-ui/react'
import { navigate } from '@reach/router'
import {
  CreateScoreMutation,
  CreateScoreMutationVariables,
  WodForNewScoreFragment as Wod,
} from './new-score.generated'
import { loggedUserId } from '../graphql/state/app'
import { MutationTuple } from '@apollo/client'

type Props = {
  wod: Wod
  mutationTupleCreateScore: MutationTuple<
    CreateScoreMutation,
    CreateScoreMutationVariables
  >
}

export const NewScore: React.FC<Props> = (props) => {
  const { wod, mutationTupleCreateScore } = props
  const userId = loggedUserId()
  // TODO we could just pass the function and handle loading/error in the page
  const [
    createScore,
    { loading: mutationLoading, error: mutationError },
  ] = mutationTupleCreateScore

  const [value, setValue] = useState(Number.NaN)

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    createScore({
      variables: {
        userId,
        wodId: wod.id,
        value,
      },
    })
    navigate('/wod-list')
  }

  if (mutationLoading) return <Spinner />
  // TODO properly handle error
  if (mutationError) return <p>Error {mutationError}</p>

  return (
    <>
      <Text>{wod.workout.description}</Text>

      <Heading fontSize='lg' my={6}>
        Add my score
      </Heading>

      <form onSubmit={handleSubmit}>
        <Box bg='_thunder' rounded='md' p={4}>
          <VStack spacing={4}>
            <NumberInput>
              <NumberInputField
                onChange={(e) => setValue(parseInt(e.target.value))}
              />
              <NumberInputStepper>
                <NumberIncrementStepper />
                <NumberDecrementStepper />
              </NumberInputStepper>
            </NumberInput>

            <Box w='100%'>
              <Button type='submit' colorScheme='blue'>
                Add my score
              </Button>
            </Box>
          </VStack>
        </Box>
      </form>
    </>
  )
}
