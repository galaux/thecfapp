import { borderRadius, edgeSize } from '../../styles/theme'
import Div from '../Div'
import styled from 'styled-components'

export const InputGroupWrapper = styled(Div)`
  input {
    &:first-child {
      border-bottom-right-radius: 0;
      border-top-right-radius: 0;
      border-right: 0;
    }
  }
  button {
    border-radius: ${borderRadius.xxxsmall};
    padding-left: ${edgeSize.xsmall};
    padding-right: ${edgeSize.xsmall};
    &:last-child {
      border-bottom-left-radius: 0;
      border-top-left-radius: 0;
    }
  }
`
