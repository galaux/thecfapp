import * as React from 'react';
import Head from 'next/head';
import { Box } from '@chakra-ui/core';

import { Topbar } from 'components/Topbar/Topbar';

export const Layout: React.FC = ({ children }) => (
  <>
    <Head>
      <link rel='icon' href='/favicon.ico' />
      <title>THECFAPP</title>
    </Head>

    <Box bg='gray.800' color='white'>
      <Topbar />
      <main>{children}</main>
    </Box>
  </>
);
