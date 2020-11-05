import * as React from 'react';
import Head from 'next/head';
import { Box } from '@chakra-ui/core';

import { Topbar, TOPBAR_HEIGHT } from 'components/Topbar/Topbar';

export const Layout: React.FC = ({ children }) => (
  <>
    <Head>
      <link rel='icon' href='/favicon.ico' />
      <title>THECFAPP</title>
    </Head>

    <Topbar />

    <Box bg='_shark' color='white' minH='100vh' p={6} pt={TOPBAR_HEIGHT}>
      {children}
    </Box>
  </>
);
