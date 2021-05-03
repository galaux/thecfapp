import React, { FC } from 'react';
import { WodForWodCardFragment as Wod } from '../../components/WodCard';
import Div from '../../common/Div';
import { Directions } from '../../types';
import { Day, Month } from './Date.styled';

type DateProps = {
  wod: Wod;
}

const Date: FC<DateProps> = ({ wod }) => {
  // const formatedDate = new Date(wod.date)
  // const month = formatedDate.toLocaleString('default', { month: 'short' })
  // const day = formatedDate.getUTCDate()

  return (
    <Div direction={ Directions.column }>
      <Month>apr</Month>
      <Day>22</Day>
    </Div>
  );
}

export default Date;
