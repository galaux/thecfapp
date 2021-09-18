import styled from 'styled-components';
import Div from '../../common/Div';
import { edgeSize, FontWeights, text } from '../../styles/theme';

export const MemberCountChipWrapper = styled(Div)`
  padding: calc(${edgeSize.xxxsmall} / 2) ${edgeSize.xxsmall};
`;

export const Count = styled.span`
  font-size: ${text.xxsmall.size};
  line-height: ${text.xxsmall.height};
  font-weight: ${FontWeights.Medium};
  letter-spacing: 0.4px;
  margin-left: ${edgeSize.xxsmall};
`;
