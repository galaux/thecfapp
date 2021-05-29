import { Body } from '../Body'
import { ErrorOr } from '../ErrorOr'
import { Header } from '../Header'
import { Layout } from '../Layout'
import { LoadingOr } from '../LoadingOr'
import { WodForNewScorePanelFragment as Wod } from './new-score-panel.generated'
import { ApolloError } from '@apollo/client'
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
} from '@chakra-ui/react'
import React, { useState } from 'react'

type Props = {
  wod: Wod
  createScore: (wodId: string, value: number) => void
  createLoading: boolean
  createError: ApolloError | undefined
}

export const NewScorePanel: React.FC<Props> = (props) => {
  const { wod, createLoading, createError, createScore } = props

  const [value, setValue] = useState(Number.NaN)

  return (
    <Layout>
      <Header>Add Score</Header>
      <Body>
        <LoadingOr loading={createLoading}>
          <ErrorOr error={createError}>
            {wod && (
              <>
                <Text>{wod.workout.description}</Text>

                <Heading fontSize='lg' my={6}>
                  Add my score
                </Heading>

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
                      <Button
                        type='submit'
                        colorScheme='blue'
                        onClick={() => createScore(wod.id, value)}
                      >
                        Add my score
                      </Button>
                    </Box>
                  </VStack>
                </Box>
              </>
            )}
          </ErrorOr>
        </LoadingOr>
      </Body>
    </Layout>
  )
}
