import * as React from 'react';
import { Flex, Heading } from '@chakra-ui/core';
import * as Icon from 'react-feather';

export const TOPBAR_HEIGHT = '4.5rem';

export const Topbar: React.FC = () => (
  <Flex
    pos='fixed'
    top={0}
    right={0}
    left={0}
    justifyContent='space-between'
    p={6}
    h={TOPBAR_HEIGHT}
    color='white'
    zIndex={10}
  >
    <Icon.Menu />

    <Heading as='h1' fontSize='xl'>
      THECFAPP
    </Heading>

    <Icon.User />
  </Flex>
);
