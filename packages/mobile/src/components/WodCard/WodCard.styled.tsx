import styled from 'styled-components';
import Div from '../../common/Div';
import { boxShadow, colors, edgeSize, FontWeights, text } from '../../styles/theme';
import { pxToRem } from '../../mixins/pxToRem';

export const WodCardWrapper = styled(Div)`
  position: relative;
  overflow: hidden;
  box-shadow: ${boxShadow.card};
  min-height: ${pxToRem(340)}rem;
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

export const Workout = styled.p`
  color: ${colors.white};
  font-size: ${text.xsmall.size};
  line-height: ${text.xsmall.height};
  font-weight: ${FontWeights.Regular};
`;
