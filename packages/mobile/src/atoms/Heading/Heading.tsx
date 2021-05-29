import { StyledHeading } from './Heading.styled'
import { HeadingProps } from './Heading.types'
import React, { FC } from 'react'

const Heading: FC<HeadingProps> = ({
  className,
  id,
  children,
  level = 1,
  textAlign,
  ...rest
}) => (
  <StyledHeading
    {...rest}
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    as={`h${level}` as any}
    className={className}
    textAlign={textAlign}
    id={id}
  >
    {children}
  </StyledHeading>
)
export default Heading
