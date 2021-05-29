import AppComponent from './App'
import getApolloClient from './graphql/apollo-client'
import { ApolloClient, NormalizedCacheObject } from '@apollo/client'
import { Spinner } from '@chakra-ui/react'
import React, { useEffect, useState } from 'react'

export default function App() {
  const [client, setClient] = useState<ApolloClient<NormalizedCacheObject>>()

  useEffect(() => {
    getApolloClient().then((client) => setClient(client))
  }, [])

  return client ? <AppComponent client={client} /> : <Spinner />
}
