import styled from 'styled-components';
import { pxToRem } from '../../mixins/pxToRem';
import { colors, FontWeights } from '../../styles/theme';

export const Month = styled.span`
  font-size: ${pxToRem(20)}rem;
  line-height: 1.2;
  color: ${colors.white};
  font-weight: ${FontWeights.SemiBold};
`;

export const Day = styled.span`
  font-size: ${pxToRem(20)}rem;
  line-height: 1.2;
  color: ${colors.white};
  font-weight: ${FontWeights.SemiBold};
  letter-spacing: 0.5px;
`;
