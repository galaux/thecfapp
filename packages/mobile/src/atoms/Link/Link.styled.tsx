import { borderWidth, colors, FontWeights, text } from '../../styles/theme'
import { ButtonVariant, LinkVariant } from '../../types'
import { buttonCss } from '../Button/Button.styled'
import { Link as LinkRouter } from '@reach/router'
import styled, { css } from 'styled-components'

export const linkCss = css`
  cursor: pointer;
  text-decoration: none;
  color: ${colors.white};
  font-size: ${text.xsmall.size};
  line-height: ${text.xsmall.height};
  font-weight: ${FontWeights.Regular};
  border-bottom: ${borderWidth.medium} solid ${colors.white};
  transition: all 0.3s ease;

  &:hover {
    color: ${colors.gray30};
    border-bottom-color: ${colors.gray30};
  }
`

type StyledLinkProps = {
  $variant: ButtonVariant | LinkVariant
}

export const StyledLink = styled(LinkRouter)<StyledLinkProps>`
  ${({ $variant }) => {
    if ($variant === ButtonVariant.Primary) return buttonCss
    return linkCss
  }}
`
