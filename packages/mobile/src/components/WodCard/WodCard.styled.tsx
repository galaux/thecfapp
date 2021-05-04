import styled from 'styled-components';
import Div from '../../common/Div';
import { borderRadius, colors, edgeSize, FontWeights, text } from '../../styles/theme';
import { pxToRem } from '../../mixins/pxToRem';
import Card from '../../atoms/Card';

export const WodCardWrapper = styled(Card)`
  position: relative;
  min-height: ${pxToRem(340)}rem;
  padding-top: ${edgeSize.large};
  &:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    width: 100%;
    height: ${pxToRem(8)}rem;
    background-color: ${colors.denim};
  }
`;

export const Type = styled.p`
  color: ${colors.gray50};
  font-size: ${text.small.size};
  line-height: ${text.small.height};
  font-weight: ${FontWeights.Medium};
  text-transform: uppercase;
  letter-spacing: 3.6px;
  padding-right: ${edgeSize.small};
`;

export const Workout = styled(Div)`
  &:not(:last-child) {
    margin-bottom: ${edgeSize.xsmall};
  }
`

export const WorkoutName = styled.p`
  color: ${colors.white};
  font-size: ${text.xsmall.size};
  line-height: ${text.xsmall.height};
  font-weight: ${FontWeights.Medium};
  letter-spacing: 0.3px;
  margin-bottom: ${edgeSize.xxxsmall};
`;

export const WorkoutInfo = styled.p`
  color: ${colors.gray50};
  font-size: ${text.xxsmall.size};
  line-height: ${text.xxsmall.height};
  font-weight: ${FontWeights.Medium};
  letter-spacing: 0.3px;
  display: flex;
  align-items: center;
`;

export const Separator = styled.span`
  background-color: ${colors.gray50};
  width: 2px;
  height: 2px;
  display: inline-block;
  margin: 0 ${edgeSize.xxsmall};
  border-radius: ${borderRadius.round};
`
