import * as React from 'react';
import { Box } from '@chakra-ui/core';
import SwipeableViews from 'react-swipeable-views';

import { WodSliderItem } from './WodSliderItem';

const wods = [
  {
    id: '000-000-000',
    name: 'DT',
    desc: '5 Rounds For Time',
  },
  {
    id: '111-111-111',
    name: 'Murph',
    desc: 'For Time. RX with a Weighted Vest',
  },
  {
    id: '222-222-222',
    name: 'Loredo',
    desc: '6 Rounds For Time',
  },
];

export const WodSlider: React.FC = () => (
  <Box mx={-4}>
    <SwipeableViews
      style={{ padding: '0 1.5rem' }}
      slideStyle={{ padding: '0 0.5rem' }}
      enableMouseEvents
    >
      {wods.map((wod) => (
        <WodSliderItem key={wod.id} wod={wod} />
      ))}
    </SwipeableViews>
  </Box>
);
