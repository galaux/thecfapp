import {
  borderRadius,
  borderWidth,
  colors,
  edgeSize,
  FontWeights,
  text,
} from '../../styles/theme'
import { ButtonVariant, LinkVariant, SideTypes } from '../../types'
import { getMargin } from '../../utils/helpers'
import styled, { css } from 'styled-components'

export type StyledCTAButtonProps = {
  disabled?: boolean
  $variant: ButtonVariant | LinkVariant
  margin?: SideTypes
}

export const buttonCss = css<StyledCTAButtonProps>`
  align-items: center;
  border-radius: ${borderRadius.small};
  display: flex;
  font-size: ${text.xsmall.size};
  line-height: 1.3;
  font-family: inherit;
  justify-content: center;
  width: auto;
  height: 2rem;
  padding: 0 ${edgeSize.medium};
  text-align: center;
  text-decoration: none;
  transition: all 0.2s ease-in-out;
  font-weight: ${FontWeights.Medium};
  cursor: pointer;
  letter-spacing: 0.4px;
  &:active {
    transform: scale(0.95);
  }
  &:focus {
    outline: none;
    border: ${borderWidth.large} dashed ${colors.gray80};
  }
  ${({ disabled }) =>
    disabled &&
    `
    opacity: .7;
    cursor: auto;
  `};
  ${(props) => getMargin(props.margin)}
  ${({ $variant }) => {
    switch (true) {
      case $variant === ButtonVariant.Primary:
        return `
          background: ${colors.denim};
          border: ${borderWidth.large} solid ${colors.denim};
          color: ${colors.white};
          &:hover { 
            background: ${colors.denimDarken}; 
            border-color: ${colors.denimDarken}; 
          }
        `
      case $variant === ButtonVariant.Secondary:
      default:
        return `
          background: ${colors.white};
          border: ${borderWidth.large} solid ${colors.white};
          color: ${colors.denim};
          &:hover { 
            background: ${colors.white}; 
            border-color: ${colors.white}; 
          }
        `
    }
  }}
`

export const StyledCTAButton = styled.button`
  ${buttonCss}
`
