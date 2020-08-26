import * as React from 'react';
import Head from 'next/head';
import { Box } from '@chakra-ui/core';

import { H1 } from 'components/H1/H1';
import { Topbar } from 'components/Topbar/Topbar';

export const Layout: React.FC = ({ children }) => (
  <>
    <Head>
      <link rel='icon' href='/favicon.ico' />
      <title>THECFAPP</title>
    </Head>

    <Box bg='gray.800' color='white' padding={4}>
      <Topbar />

      <Box as='main' mt={6}>
        <H1>WOD</H1>
        {children}
      </Box>
    </Box>
  </>
);
