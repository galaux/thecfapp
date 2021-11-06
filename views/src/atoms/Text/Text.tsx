import React, { FC } from 'react';
import { TextWrapper } from './Text.styled';
import { TextProps } from './Text.types';

const Text: FC<TextProps> = ({ children, ...props }) => (
  <TextWrapper { ...props }>{children}</TextWrapper>
);

export default Text;
