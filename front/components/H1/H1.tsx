import * as React from 'react';
import { Heading, HeadingProps, Text } from '@chakra-ui/core';

export interface H1Props extends HeadingProps {
  isFaded?: boolean;
  subText?: string;
}

export const H1: React.FC<H1Props> = ({
  children,
  isFaded,
  subText,
  ...props
}) => (
  <Heading
    as='h1'
    fontSize='1.25rem'
    fontWeight={400}
    textTransform='uppercase'
    mb={6}
    color={isFaded ? 'gray.500' : 'white'}
    {...props}
  >
    {children}

    {subText && (
      <Text
        as='span'
        display='block'
        color='white'
        textTransform='initial'
        mt={2}
      >
        {subText}
      </Text>
    )}
  </Heading>
);
