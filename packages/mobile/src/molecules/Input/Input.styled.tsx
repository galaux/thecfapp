import {
  borderRadius,
  borderWidth,
  colors,
  edgeSize,
  FontWeights,
  text,
} from '../../styles/theme'
import styled from 'styled-components'

export const InputStyled = styled.input`
  width: 100%;
  border: ${borderWidth.medium} solid ${colors.gray10};
  border-radius: ${borderRadius.xxxsmall};
  font-size: ${text.small.size};
  line-height: ${text.small.height};
  padding: 0 ${edgeSize.xxsmall};
  font-weight: ${FontWeights.Medium};
  transition: border-color 300ms ease;
  background-color: ${colors.white};
  color: ${colors.gray60};
  font-family: inherit;
  outline: none;
  height: 2rem;
  &::placeholder {
    color: ${colors.gray30};
  }
  &::-webkit-outer-spin-button,
  &::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
  &:disabled {
    opacity: 0.7;
    background-color: ${colors.gray10};
  }
  &:focus {
    border-color: ${colors.denim};
  }
`
