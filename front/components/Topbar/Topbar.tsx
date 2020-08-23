import * as React from 'react';
import { Flex, Heading } from '@chakra-ui/core';
import { Menu, User } from 'react-feather';

export const Topbar: React.FC = () => (
  <Flex justifyContent='space-between' padding={4}>
    <Menu />
    <Heading as='h1' fontSize='lg'>
      THECFAPP
    </Heading>
    <User />
  </Flex>
);
