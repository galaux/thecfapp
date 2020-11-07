import * as React from 'react';
import { Box } from '@chakra-ui/core';

export const Body: React.FC = ({ children }) => (
  <Box gridArea='Body' p={6} overflow='scroll'>
    {children}
  </Box>
);
