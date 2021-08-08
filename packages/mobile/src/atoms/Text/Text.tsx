import { TextWrapper } from './Text.styled'
import { TextProps } from './Text.types'
import React, { FC } from 'react'

const Text: FC<TextProps> = ({ children, ...props }) => (
  <TextWrapper {...props}>{children}</TextWrapper>
)

export default Text
