import React, { FormEvent, useState } from 'react'
import { IWod } from '../types'
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
import { useMutation, gql } from '@apollo/client'
import { CREATE_SCORE } from '../graphql/queries/score'
import { userId } from '../apollo-client'
import { navigate } from '@reach/router'

type Props = {
  wod: IWod
}

export const Score: React.FC<Props> = (props) => {
  const { wod } = props

  const [value, setValue] = useState<number | null>(null)

  const [
    createScore,
    { loading: mutationLoading, error: mutationError },
  ] = useMutation(CREATE_SCORE, {
    update(cache, { data: { createScore } }) {
      cache.modify({
        fields: {
          scores(existingScores = []) {
            const newScoreRef = cache.writeFragment({
              data: createScore,
              fragment: gql`
                fragment NewScore on Score {
                  id
                  user {
                    id
                  }
                  value
                  wod {
                    id
                  }
                }
              `,
            })
            return [...existingScores, newScoreRef]
          },
        },
      })
    },
  })

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
  if (mutationError) return <p>Error {mutationError}</p> // TODO

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
