import { ApolloError } from '@apollo/client'
import { Flex, Text } from '@chakra-ui/react'
import React, { PropsWithChildren } from 'react'
import * as Icon from 'react-feather'

interface Props {
  error: ApolloError | undefined
}

export const ErrorOr: React.FC<PropsWithChildren<Props>> = (props) => {
  const { error, children } = props

  if (!error) {
    return <>{children}</>
  }

  console.error(error)

  return (
    <Flex
      bg='red.800'
      justify='center'
      alignItems='center'
      direction='column'
      fontSize='sm'
      p={4}
      rounded={4}
      {...props}
    >
      <Icon.Frown size='1.4rem' />
      <Text mt={2} fontWeight='600'>
        Oups, something went wrong
      </Text>
    </Flex>
  )
}
