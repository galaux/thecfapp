import { ApolloClient, NormalizedCacheObject } from '@apollo/client'
import React, { useEffect, useState } from 'react'
import { Spinner } from '@chakra-ui/react'

import getApolloClient from './graphql/apollo-client'
import AppComponent from './App'

export default function App() {
  const [client, setClient] = useState<ApolloClient<NormalizedCacheObject>>()

  useEffect(() => {
    getApolloClient().then((client) => setClient(client))
  }, [])

  return client ? <AppComponent client={client} /> : <Spinner />
}
