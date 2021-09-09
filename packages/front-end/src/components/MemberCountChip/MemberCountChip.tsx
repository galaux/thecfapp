import React, { FC } from 'react';
import { Users } from 'react-feather';
import { BorderRadius, Colors } from '../../styles/theme';
import { Aligns, Directions, Justifys } from '../../types';
import { Count, MemberCountChipWrapper } from './MemberCountChip.styled';
import { MemberCountChipProps } from './MemberCountChip.type';

const MemberCountChip: FC<MemberCountChipProps> = ({
  count,
  color = Colors.denimDarken,
}) => (
  <MemberCountChipWrapper
    backgroundColor={ color }
    direction={ Directions.row }
    align={ Aligns.center }
    radius={ BorderRadius.Xsmall }
    justify={ Justifys.between }
    color={ Colors.white }
  >
    <Users size={ 12 } />
    <Count>{count}</Count>
  </MemberCountChipWrapper>
);

export default MemberCountChip;
