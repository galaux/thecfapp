import * as React from 'react';
import { Box, Heading, Text } from '@chakra-ui/core';

import { IWod } from 'types/wod.d';

export interface WodSliderItemProps {
  wod: IWod;
}

export const WodSliderItem: React.FC<WodSliderItemProps> = ({ wod }) => (
  <Box w='100%' h='100%' bg='_thunder' rounded='md' p={4}>
    <Box mb={4}>
      <Heading
        as='h1'
        fontSize='1.25rem'
        fontWeight={400}
        textTransform='uppercase'
        color='_gray'
      >
        {wod.name}
      </Heading>
      <Text mt={1}>{wod.desc}</Text>
    </Box>

    {wod.movements.map((movement) => (
      <Box mb={2} key={movement.id}>
        <Text fontSize='sm'>{movement.name}</Text>
        <Text fontSize='sm' color='_gray'>
          {`${movement.reps} reps`}
          {movement.rx_male &&
            movement.rx_female &&
            ` @ ${movement.rx_male}/${movement.rx_female} ${movement.unit}`}
        </Text>
      </Box>
    ))}
  </Box>
);
