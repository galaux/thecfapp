import * as React from 'react';
import { Flex, Text } from '@chakra-ui/core';

interface IUserStat {
  count: string;
}

export const UserStat: React.FC<IUserStat> = ({ count, children }) => (
  <Flex alignItems='center'>
    <Text fontSize='2rem' mr={2}>
      {count}
    </Text>

    <Text
      color='_gray'
      fontSize='0.75rem'
      textTransform='uppercase'
      lineHeight={1.3}
    >
      {children}
    </Text>
  </Flex>
);
