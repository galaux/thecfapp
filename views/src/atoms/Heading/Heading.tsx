import React, { FC } from 'react';
import { StyledHeading } from './Heading.styled';
import { HeadingProps, HeadingTag } from './Heading.types';

const Heading: FC<HeadingProps> = ({
  className,
  children,
  level = 1,
  ...rest
}) => (
  <StyledHeading
    { ...rest }
    as={ `h${level}` as HeadingTag }
    className={ className }
  >
    {children}
  </StyledHeading>
);
export default Heading;
