import * as React from 'react';
import { Box } from '@chakra-ui/core';

export const Layout: React.FC = ({ children }) => (
  <Box bg='_shark' color='white' minH='100vh'>
    {children}
  </Box>
);
