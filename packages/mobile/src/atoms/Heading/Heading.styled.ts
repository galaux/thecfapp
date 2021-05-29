import { colors, Colors } from '../../styles/theme'
import { getMargin, getPadding } from '../../utils/helpers'
import { HeadingStyledProps } from './Heading.types'
import { getHeadingSize } from './Heading.utils'
import styled from 'styled-components'

export const StyledHeading = styled.h1<HeadingStyledProps>`
  ${({ margin }) => getMargin(margin)};
  ${({ pad }) => getPadding(pad)};
  ${({ as }) => getHeadingSize(as!)};
  color: ${({ color = Colors.white }) => colors[color]};
  text-align: ${({ textAlign }) => textAlign};
  font-weight: ${({ weight }) => weight};
  letter-spacing: 0.4px;
`
