import { pxToRem } from '../../mixins/pxToRem';
import Button from '../../atoms/Button';
import styled from 'styled-components';
import { colors, edgeSize, text } from '../../styles/theme';
import Link from '../../atoms/Link';

export const ButtonStyled = styled(Button)`
  min-width: ${pxToRem(160)}rem;
`;

export const Separator = styled.p`
  margin: ${edgeSize.xsmall} 0;
  font-size: ${text.xsmall.size};
  line-height: ${text.xsmall.height};
  color: ${colors.gray50};
`;

export const ButtonFacebook = styled(Link)`
  background-color: ${colors.denimDarken};
  border-color: ${colors.denimDarken};
`;

export const Text = styled.p`
  font-size: ${text.xsmall.size};
  line-height: ${text.xsmall.height};
  color: ${colors.white};`;

