import * as React from 'react';
import { useRouter } from 'next/router';
import { Box, Button, Heading, Text, VStack } from '@chakra-ui/core';

import { IWod } from 'types/wod';

interface IWodSliderItem {
  wod: IWod;
}

export const WodSliderItem: React.FC<IWodSliderItem> = ({ wod }) => {
  const router = useRouter();
  const { workout } = wod;

  const onAddScoreBtnClick = () => {
    console.log(wod);
    router.push(`${router.asPath}/wod/${wod.id}/add-score`);
  };

  return (
    <Box w='100%' h='100%' bg='_thunder' rounded='md' p={4}>
      <VStack spacing={2} align='flex-start'>
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
        <Text>{workout.description}</Text>

        <Button onClick={onAddScoreBtnClick} colorScheme='blue'>
          Add a score
        </Button>
      </VStack>

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
};
