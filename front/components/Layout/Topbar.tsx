import * as React from 'react';
import * as Icon from 'react-feather';
import { Flex, FlexProps, Heading } from '@chakra-ui/core';

export const Topbar: React.FC<FlexProps> = (props) => (
  <Flex
    justifyContent='space-between'
    p={6}
    color='white'
    zIndex={10}
    {...props}
  >
    <Icon.Menu />

    <Heading as='h1' fontSize='xl'>
      THECFAPP
    </Heading>

    <Icon.User />
  </Flex>
);
