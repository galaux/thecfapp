import React, { FC, MouseEventHandler } from 'react';
import { ButtonVariant, LinkVariant } from '../../types';
import { StyledLink } from './Link.styled';

type LinkProps = {
  to: string
  onClick?: MouseEventHandler
  className?: string
  variant?: LinkVariant | ButtonVariant
};

const Link: FC<LinkProps> = ({
  children,
  to,
  onClick,
  variant = LinkVariant.Default,
  className,
  ...rest
}) => (
  <StyledLink
    { ...rest }
    to={ to }
    onClick={ onClick }
    $variant={ variant }
    className={ className }
  >
    {children}
  </StyledLink>
);

export default Link;
