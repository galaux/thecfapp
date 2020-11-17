import * as React from 'react';
import { Box, Heading, Text } from '@chakra-ui/core';

import { IWorkout } from 'types/workout.d';

interface IWodSliderItem {
  workout: IWorkout;
}

export const WodSliderItem: React.FC<IWodSliderItem> = ({ workout }) => (
  <Box w='100%' h='100%' bg='_thunder' rounded='md' p={4}>
    <Box mb={4}>
      <Heading
        as='h1'
        fontSize='1.25rem'
        fontWeight={400}
        lineHeight={1.2}
        textTransform='uppercase'
        color='_gray'
      >
        {workout.name}
      </Heading>
      <Text mt={1}>{workout.description}</Text>
    </Box>

    {/* {workout.movements.map((movement) => (
      <Box mb={2} key={movement.id}>
        <Text fontSize='sm'>{movement.name}</Text>
        <Text fontSize='sm' color='_gray'>
          {`${movement.reps} reps`}
          {movement.rx_male &&
            movement.rx_female &&
            ` @ ${movement.rx_male}/${movement.rx_female} ${movement.unit}`}
        </Text>
      </Box>
    ))} */}
  </Box>
);
