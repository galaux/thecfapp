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
import { WodForNewScorePanelFragment as Wod } from './new-score-panel.generated'
import { ApolloError } from '@apollo/client'
import { Body, Header, Layout } from '../../Layout'

type Props = {
  wod: Wod
  createScore: (wodId: string, value: number) => void
  createLoading: boolean
  createError: ApolloError | undefined
}

export const NewScorePanel: React.FC<Props> = (props) => {
  const { wod, createLoading, createError, createScore } = props

  const [value, setValue] = useState(Number.NaN)

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    createScore(wod.id, value)
    navigate('/wod-list')
  }

  return (
    <Layout>
      <Header>Add Score</Header>
      <Body>
        {createLoading && <Spinner />}
        {
          createError && <p>Error {createError}</p>
          /* TODO properly handle error */
        }
        {wod && (
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
        )}
      </Body>
    </Layout>
  )
}
