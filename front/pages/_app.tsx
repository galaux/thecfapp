import * as React from 'react';
import { AppProps } from 'next/app';
import { ApolloProvider } from '@apollo/client';
import { ChakraProvider, extendTheme } from '@chakra-ui/core';

import { useApollo } from 'utils/apollo';

const colors = {
  _gray: '#808080',
  _shark: '#1D1D22',
  _thunder: '#2F2A2F',
};

const customTheme = extendTheme({ colors });

const App: React.FC<AppProps> = ({ Component, pageProps }) => (
  <ApolloProvider client={useApollo(pageProps.initialApolloState)}>
    <ChakraProvider theme={customTheme}>
      <Component {...pageProps} />
    </ChakraProvider>
  </ApolloProvider>
);

export default App;
