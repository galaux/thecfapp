import { ButtonVariant, SideTypes } from '../../types';
import styled from 'styled-components';
import { HTMLProps } from 'react';
import { borderRadius, borderWidth, colors, edgeSize, FontWeights, text } from '../../styles/theme';
import { getMargin } from '../../utils/helpers';

type StyledCTAButtonProps = HTMLProps<HTMLButtonElement> & {
  disabled?: boolean;
  variant: ButtonVariant;
  margin?: SideTypes;
};

const StyledCTAButton = styled.button<StyledCTAButtonProps>`
  align-items: center;
  border-radius: ${borderRadius.small};
  display: flex;
  font-size: ${text.xsmall.size};
  line-height: 1.3;
  font-family: inherit;
  justify-content: center;
  width: auto;
  padding: ${edgeSize.xxsmall} ${edgeSize.medium};
  text-align: center;
  text-decoration: none;
  transition: all 0.2s ease-in-out;
  font-weight: ${FontWeights.Medium};
  cursor: pointer;
  letter-spacing: 0.4px;
  &:active { 
    transform: scale(0.95);
  }
  &:focus {
    outline: none;
    border: ${borderWidth.large} dashed ${colors.gray80};
  }
  ${(props) => getMargin(props.margin)}
  ${({variant}) => {
    switch(true) {
      case variant === ButtonVariant.Primary:
        return `
          background: ${colors.denim};
          border: ${borderWidth.large} solid ${colors.denim};
          color: ${colors.white};
          &:hover { 
            background: ${colors.denim}; 
            border-color: ${colors.denim}; 
          }
        `
      case variant === ButtonVariant.Secondary:
      default:
        return `
          background: ${colors.white};
          border: ${borderWidth.large} solid ${colors.white};
          color: ${colors.denim};
          &:hover { 
            background: ${colors.white}; 
            border-color: ${colors.white}; 
          }
        `
    }
  }}
  ${(disabled) => disabled && `
    opacity: .9;
    cursor: auto;
  `}
`;

export default StyledCTAButton;
