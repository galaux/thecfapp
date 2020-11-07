import * as React from 'react';
import Head from 'next/head';
import { Box, Grid, Text } from '@chakra-ui/core';

import { Topbar } from '.';

const templateAreas = `
  'Header'
  'Body'
  'Footer'
`;

export const Layout: React.FC = ({ children }) => (
  <>
    <Head>
      <link rel='icon' href='/favicon.ico' />
      <title>THECFAPP</title>
    </Head>

    <Grid
      h='100vh'
      templateAreas={templateAreas}
      templateColumns='1fr'
      templateRows='auto 1fr auto'
      bg='_shark'
      color='white'
    >
      <Topbar gridArea='Header' />

      <Box gridArea='Body' p={6} overflow='scroll'>
        {children}
      </Box>

      <Text gridArea='Footer' fontSize='xs' color='_gray' px={6} py={3}>
        © 2020 TheCFApp. All rights reserved
      </Text>
    </Grid>
  </>
);
