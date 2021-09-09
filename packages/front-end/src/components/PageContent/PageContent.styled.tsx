import styled from 'styled-components';
import Div from '../../common/Div';
import { edgeSize } from '../../styles/theme';

export const PageContentWrapper = styled(Div)`
  height: 100%;
  flex: 1;
`;

export const Content = styled(Div)`
  padding: ${edgeSize.xxlarge} ${edgeSize.xxlarge} 0;
`;
