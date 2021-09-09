import styled from 'styled-components';
import { Colors, colors, FontWeights, text } from '../../styles/theme';
import { FontSize, TextAlign } from '../../types';
import { TextProps } from './Text.types';

export const TextWrapper = styled.p<TextProps>`
  ${({ size = FontSize.Small }) => `
    font-size: ${text[size].size};
    line-height: ${text[size].height};
  `};
  font-weight: ${({ weight = FontWeights.Regular }) => weight};
  color: ${({ color = Colors.white }) => colors[color]};
  text-align: ${({ textAlign = TextAlign.Left }) => textAlign};
`;
