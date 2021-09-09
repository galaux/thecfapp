import React, { FC } from 'react';
import { Directions } from '../../types';
import { LayoutWrapper } from './Layout.styled';

const Layout: FC = ({ children }) => (
  <LayoutWrapper direction={ Directions.column }>
    {children}
  </LayoutWrapper>
);

export default Layout;
