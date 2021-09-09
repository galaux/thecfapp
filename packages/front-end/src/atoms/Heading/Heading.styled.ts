import styled from 'styled-components';
import { colors, Colors } from '../../styles/theme';
import { getMargin, getPadding } from '../../utils/helpers';
import { HeadingStyledProps } from './Heading.types';
import { getHeadingDefaultSize, getHeadingSize } from './Heading.utils';

export const StyledHeading = styled.h1<HeadingStyledProps>`
  ${({ margin }) => getMargin(margin)};
  ${({ pad }) => getPadding(pad)};
  ${({ size, as }) => (size ? getHeadingSize(size) : getHeadingDefaultSize(as))}
  color: ${({ color = Colors.white }) => colors[color]};
  text-align: ${({ textAlign }) => textAlign};
  font-weight: ${({ weight }) => weight};
  ${({ uppercase }) => (uppercase ? 'text-transform: uppercase' : null)};
  letter-spacing: 0.4px;
`;
