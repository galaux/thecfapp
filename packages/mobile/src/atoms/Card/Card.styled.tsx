import Div from '../../common/Div'
import { boxShadow, edgeSize } from '../../styles/theme'
import styled from 'styled-components'

export const CardWrapper = styled(Div)`
  overflow: hidden;
  box-shadow: ${boxShadow.card};
  &:not(:last-child) {
    margin-bottom: ${edgeSize.medium};
  }
`
