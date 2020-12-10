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
import { gql } from '@apollo/client'
import { userId } from '../graphql/apollo-client'
import { navigate } from '@reach/router'
import { useCreateScoreMutation } from './create-score.generated'
import { GetWodWodFragment } from './get-wod.generated'

type Props = {
  wod: GetWodWodFragment
}

export const NewScore: React.FC<Props> = (props) => {
  const { wod } = props

  const [value, setValue] = useState(Number.NaN)

  const [
    createScore,
    { loading: mutationLoading, error: mutationError },
  ] = useCreateScoreMutation({
    update(cache, { data }) {
      // TODO handle no data returned as an error
      if (data) {
        cache.modify({
          fields: {
            scores(existingScores = []) {
              const newScoreRef = cache.writeFragment({
                data: data.createScore,
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
      } // TODO handle case where data is undefined?
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
