import React, { FC } from 'react';
import Div from '../../common/Div';
import { Aligns, Directions } from '../../types';
import { Day, Month } from './Date.styled';

type DateProps = {
  // eslint-disable-next-line
  wod: any
};

const Date: FC<DateProps> = ({ wod }) => {
  const formatedDate = new window.Date(wod.date);
  const month = formatedDate.toLocaleString('en-EN', { month: 'short' });
  const day = formatedDate.getUTCDate();

  return (
    <Div direction={ Directions.column } align={ Aligns.center }>
      <Month>{month}</Month>
      <Day>{day}</Day>
    </Div>
  );
};

export default Date;
