import * as React from 'react';
import { Box, SimpleGrid, Text } from '@chakra-ui/core';
import { TOPBAR_HEIGHT } from 'components/Topbar/Topbar';

import { UserStat } from './UserStat';

const bgImg = 'assets/meghan-holmes-buWcS7G1_28-unsplash.jpg';

export const UserStats: React.FC = () => (
  <Box
    pos='relative'
    pt={TOPBAR_HEIGHT}
    px={6}
    bgColor='_thunder'
    borderBottomLeftRadius={12}
    borderBottomRightRadius={12}
    overflow='hidden'
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
    <Box zIndex={10} pos='relative' pb={4}>
      <Box mb={4}>
        <Text color='_gray' fontSize='sm'>
          Daily Motivation
        </Text>
        <Text
          textTransform='uppercase'
          letterSpacing='0.2rem'
          fontWeight={600}
          fontSize='lg'
        >
          Keep Going!
        </Text>
      </Box>

      <SimpleGrid columns={2}>
        <UserStat count='24'>
          WOD
          <br />
          Performed
        </UserStat>

        <UserStat count='20'>
          Team
          <br />
          Mates
        </UserStat>

        <UserStat count='18'>
          WOD
          <br />
          Completed
        </UserStat>

        <UserStat count='7th'>
          Average
          <br />
          Ranking
        </UserStat>
      </SimpleGrid>
    </Box>
  </Box>
);
