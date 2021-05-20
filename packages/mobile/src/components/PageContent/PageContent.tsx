import React, { FC } from 'react';
import styled from 'styled-components';
import Div from '../../common/Div';
import { edgeSize } from '../../styles/theme';

const PageContentWrapper = styled(Div)`
  padding: ${edgeSize.xxlarge} ${edgeSize.xxlarge} ${edgeSize.medium};
`

const PageContent: FC = ({children}) => (
  <PageContentWrapper>
    {children}
  </PageContentWrapper>
);

export default PageContent;
