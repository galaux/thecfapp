import styled from 'styled-components';
import Div from '../../common/Div';
import { hexToRgb } from '../../mixins/hexToRgb';
import { borderRadius, boxShadow, colors, edgeSize } from '../../styles/theme';
import { HEADER_BACKGROUND_IMAGE } from '../../utils/constants';

export const HeaderWrapper = styled(Div)`
  border-radius: 0 0 ${borderRadius.xsmall} ${borderRadius.xsmall};
  background-color: ${colors.gray80};
  padding: 0 ${edgeSize.xxlarge} ${edgeSize.xxlarge};
  background-image: url(${HEADER_BACKGROUND_IMAGE});
  background-size: initial;
  background-position: center left;
  position: relative;
  overflow: hidden;
  box-shadow: ${boxShadow.mediumDark};
  
  &:before {
    content: '';
    position: absolute;
    right: 0;
    left: 0;
    top: 0;
    bottom: 0;
    background-color: rgba(${hexToRgb(colors.gray80)}, .9);
  }
`;

export const Content = styled(Div)`
  position: relative;
  z-index: 5;
`;
