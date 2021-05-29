import { pxToRem } from '../../mixins/pxToRem'
import { colors, FontWeights } from '../../styles/theme'
import styled from 'styled-components'

export const Month = styled.span`
  font-size: ${pxToRem(12)}rem;
  line-height: 1.2;
  color: ${colors.gray50};
  font-weight: ${FontWeights.SemiBold};
  text-transform: uppercase;
  margin-bottom: -${pxToRem(2)}rem;
  display: block;
`

export const Day = styled.span`
  font-size: ${pxToRem(20)}rem;
  line-height: 1.2;
  color: ${colors.white};
  font-weight: ${FontWeights.SemiBold};
  letter-spacing: 0.5px;
`
