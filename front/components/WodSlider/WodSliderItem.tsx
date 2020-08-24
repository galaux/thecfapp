import * as React from 'react';
import { Box } from '@chakra-ui/core';

import { H1 } from 'components/H1/H1';
import { Wod } from 'types/wod.d';

export interface WodSliderItemProps {
  wod: Wod;
}

export const WodSliderItem: React.FC<WodSliderItemProps> = ({ wod }) => (
  <Box w='100%' h='100%' bg='gray.700' rounded='md' p={4}>
    <H1 isFaded subText={wod.desc}>
      {wod.name}
    </H1>
  </Box>
);
