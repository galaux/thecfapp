import React, { FC, MouseEventHandler } from 'react';
import { StyledCTAButton } from './Button.styled';
import { ButtonType, ButtonVariant, SideTypes } from '../../types';

export type ButtonProps = {
  onClick?: MouseEventHandler;
  variant?: ButtonVariant;
  disabled?: boolean;
  className?: string;
  margin?: SideTypes;
  type?: ButtonType;
};

const Button: FC<ButtonProps> = ({
  children,
  disabled,
  variant = ButtonVariant.Primary ,
  onClick,
  className,
  type = ButtonType.button,
  margin
}) => {
  return(
    <StyledCTAButton
      disabled={ disabled }
      $variant={ variant }
      onClick={ onClick }
      className={ className }
      margin={ margin }
      type={ type }
    >
      {children}
    </StyledCTAButton>
  );
}

export default Button;
