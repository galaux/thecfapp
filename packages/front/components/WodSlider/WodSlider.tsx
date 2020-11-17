import * as React from 'react';
import SwipeableViews from 'react-swipeable-views';

import { ICommunity } from 'types/community';
import { WodSliderItem } from './WodSliderItem';

// const wods = [
//   {
//     id: '000-000-000',
//     name: 'DT',
//     desc: '5 Rounds For Time',
//     movements: [
//       {
//         id: '000-000-000',
//         name: 'Deadlifts',
//         reps: '12',
//         rx_male: '70',
//         rx_female: '50',
//         unit: 'kg',
//       },
//       {
//         id: '111-111-111',
//         name: 'Hang Power Cleans',
//         reps: '9',
//         rx_male: '70',
//         rx_female: '50',
//         unit: 'kg',
//       },
//       {
//         id: '222-222-222',
//         name: 'Push Jerks',
//         reps: '6',
//         rx_male: '70',
//         rx_female: '50',
//         unit: 'kg',
//       },
//     ],
//   },
//   {
//     id: '111-111-111',
//     name: 'Murph',
//     desc: 'For Time. RX with a Weighted Vest',
//     movements: [
//       {
//         id: '000-000-000',
//         name: 'Run',
//         reps: '1km',
//       },
//       {
//         id: '111-111-111',
//         name: 'Pull-Ups',
//         reps: '100',
//       },
//       {
//         id: '222-222-222',
//         name: 'Push-Ups',
//         reps: '200',
//       },
//       {
//         id: '333-333-333',
//         name: 'Squats',
//         reps: '300',
//       },
//       {
//         id: '444-444-444',
//         name: 'Run',
//         reps: '1km',
//       },
//     ],
//   },
// ] as IWod[];

interface IWodSlider {
  community: ICommunity;
}

export const WodSlider: React.FC<IWodSlider> = ({ community }) => (
  <SwipeableViews
    style={{ padding: '0 1.5rem' }}
    slideStyle={{ paddingRight: '1rem' }}
    enableMouseEvents
  >
    {community.wods.map((wod) => (
      <WodSliderItem key={wod.id} workout={wod.workout} />
    ))}
  </SwipeableViews>
);
