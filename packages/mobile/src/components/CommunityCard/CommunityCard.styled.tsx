import styled from 'styled-components';
import { colors, edgeSize, FontWeights, text } from '../../styles/theme';

export const CommunityName = styled.h3`
  text-transform: uppercase;
  color: ${colors.white};
  font-size: ${text.small.size};
  line-height: ${text.small.height};
  font-weight: ${FontWeights.Medium};
  letter-spacing: 3.6px;
  padding-right: ${edgeSize.small};
`

export const Address = styled.p`
  font-size: ${text.xxsmall.size};
  line-height: ${text.xxsmall.height};
  color: ${colors.gray30};
  letter-spacing: .4px;
  font-weight: ${FontWeights.Medium};
`
