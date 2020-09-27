import * as React from 'react';
import { AppProps } from 'next/app';
import { ApolloProvider } from '@apollo/client';
import { ChakraProvider } from '@chakra-ui/core';

import { useApollo } from 'utils/apollo';

const App: React.FC<AppProps> = ({ Component, pageProps }) => (
  <ApolloProvider client={useApollo(pageProps.initialApolloState)}>
    <ChakraProvider resetCSS>
      <Component {...pageProps} />
    </ChakraProvider>
  </ApolloProvider>
);

export default App;
