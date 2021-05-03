import React, { FC } from 'react'
import * as Icon from 'react-feather'
import Div from '../../common/Div';
import { BorderRadius, Colors, edgeSize, FontWeights, text } from '../../styles/theme';
import { Aligns, Directions, Justifys } from '../../types';
import styled from 'styled-components';

const UserCountChipWrapper = styled(Div)`
  padding: ${edgeSize.xxxsmall} ${edgeSize.xxsmall} calc(${edgeSize.xxxsmall}/2);
`;

const Count = styled.span`
  font-size: ${text.xxsmall.size};
  line-height: ${text.xxsmall.height};
  font-weight: ${FontWeights.Medium};
  letter-spacing: 0.4px;
  margin-left: ${edgeSize.xxsmall};
`;

export type UserCountChipProps = {
  count: number;
  color?: Colors;
}

const UserCountChip: FC<UserCountChipProps> = ({ count, color = Colors.denim }) => (
  <UserCountChipWrapper
    backgroundColor={ color }
    direction={ Directions.row }
    align={ Aligns.start }
    radius={ BorderRadius.Xsmall }
    justify={ Justifys.between }
  >
    <Icon.Users size={ 12 } />
    <Count>{count}</Count>
  </UserCountChipWrapper>
)

export default UserCountChip;
