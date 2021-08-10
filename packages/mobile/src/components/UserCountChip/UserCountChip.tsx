import Div from '../../common/Div'
import {
  BorderRadius,
  Colors,
  edgeSize,
  FontWeights,
  text,
} from '../../styles/theme'
import { Aligns, Directions, Justifys } from '../../types'
import React, { FC } from 'react'
import * as Icon from 'react-feather'
import styled from 'styled-components'

const UserCountChipWrapper = styled(Div)`
  padding: calc(${edgeSize.xxxsmall} / 2) ${edgeSize.xxsmall};
`

const Count = styled.span`
  font-size: ${text.xxsmall.size};
  line-height: ${text.xxsmall.height};
  font-weight: ${FontWeights.Medium};
  letter-spacing: 0.4px;
  margin-left: ${edgeSize.xxsmall};
`

export type UserCountChipProps = {
  count: number
  color?: Colors
}

const UserCountChip: FC<UserCountChipProps> = ({
  count,
  color = Colors.denimDarken,
}) => (
  <UserCountChipWrapper
    backgroundColor={color}
    direction={Directions.row}
    align={Aligns.center}
    radius={BorderRadius.Xsmall}
    justify={Justifys.between}
    color={Colors.white}
  >
    <Icon.Users size={12} />
    <Count>{count}</Count>
  </UserCountChipWrapper>
)

export default UserCountChip
