import Div from '../../common/Div'
import { edgeSize } from '../../styles/theme'
import React, { FC } from 'react'
import styled from 'styled-components'

const PageContentWrapper = styled(Div)`
  padding: ${edgeSize.xxlarge} ${edgeSize.xxlarge} ${edgeSize.medium};
`

const PageContent: FC = ({ children }) => (
  <PageContentWrapper>{children}</PageContentWrapper>
)

export default PageContent
