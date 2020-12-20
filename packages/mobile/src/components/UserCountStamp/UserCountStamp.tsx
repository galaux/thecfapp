import * as React from 'react'
import { Badge, Flex } from '@chakra-ui/react'
import * as Icon from 'react-feather'

export const UserCountStamp: React.FC<{ count: number }> = ({ count }) => (
  <Badge colorScheme='blue' rounded='md'>
    <Flex>
      <Icon.Users />
      {count}
    </Flex>
  </Badge>
)
