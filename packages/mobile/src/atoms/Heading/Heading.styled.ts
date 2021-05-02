import styled from 'styled-components';
import { getMargin, getPadding } from '../../utils/helpers';
import { HeadingStyledProps } from './Heading.types';
import { getHeadingSize } from './Heading.utils';
import { colors, Colors } from '../../styles/theme';

export const StyledHeading = styled.h1<HeadingStyledProps>`
  ${({ margin }) => getMargin(margin)};
  ${({ pad }) => getPadding(pad)};
  ${({ as }) => getHeadingSize(as!)};
  color: ${({ color = Colors.white }) => colors[color]};
  text-align: ${({ textAlign }) => textAlign};
`;
