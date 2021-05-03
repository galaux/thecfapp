import React, { FC } from 'react';
import { WodForWodCardFragment as Wod } from '../../components/WodCard';
import Div from '../../common/Div';
import { Directions } from '../../types';
import { Day, Month } from './Date.styled';
import { parse, format } from "date-fns";

type DateProps = {
  wod: Wod;
}

const Date: FC<DateProps> = ({ wod }) => {
  // const formatedDate = parse('2016-01-01', 'yyyy-MM-dd', new Date())
  // const month = formatedDate.toLocaleString('default', { month: 'short' })
  // const day = formatedDate.getUTCDate()
  const getHours = () => {
    const currentHours = new Date().getHours();
    return currentHours;
  }

  setTimeout(() => {const hours = getHours(); console.log(hours);}, 3000);

  return (
    <Div direction={ Directions.column }>
      {/*<Month>{month}</Month>*/}
      {/*<Day>{day}</Day>*/}
      ok
    </Div>
  );
}

export default Date;
