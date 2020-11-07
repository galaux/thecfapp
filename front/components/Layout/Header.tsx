import * as React from 'react';
import * as Icon from 'react-feather';
import { Box, Flex, Heading } from '@chakra-ui/core';

const bgImg = 'assets/meghan-holmes-buWcS7G1_28-unsplash.jpg';

export const Header: React.FC = ({ children }) => (
  <Box
    pos='relative'
    bgColor='_thunder'
    borderBottomLeftRadius={12}
    borderBottomRightRadius={12}
    overflow='hidden'
    p={6}
    _after={{
      content: '""',
      pos: 'absolute',
      top: 0,
      right: 0,
      bottom: 0,
      left: 0,
      background: `url(${bgImg})`,
      bgPos: 'center',
      bgSize: 'cover',
      opacity: 0.3,
      zIndex: 5,
    }}
  >
    <Flex
      gridArea='Header'
      justifyContent='space-between'
      pos='relative'
      zIndex={10}
      mb={6}
    >
      <Icon.Menu />

      <Heading as='h1' fontSize='xl'>
        THECFAPP
      </Heading>

      <Icon.User />
    </Flex>

    {children}
  </Box>
);
