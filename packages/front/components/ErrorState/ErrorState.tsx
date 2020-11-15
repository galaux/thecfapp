import * as React from 'react';
import * as Icon from 'react-feather';
import { Flex, Text } from '@chakra-ui/core';

export const ErrorState: React.FC = (props) => (
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
);
