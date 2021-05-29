import { Box } from '@chakra-ui/react'
import * as React from 'react'

export const Body: React.FC = ({ children }) => (
  <Box gridArea='Body' p={6} overflow='scroll'>
    {children}
  </Box>
)
