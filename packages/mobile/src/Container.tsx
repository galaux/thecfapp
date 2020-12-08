import { ApolloClient, NormalizedCacheObject } from '@apollo/client'
import React, { useEffect, useState } from 'react'

import getApolloClient from './apollo-client'
import AppComponent from './App'

export default function App() {
  const [
    client,
    setClient,
  ] = useState<ApolloClient<NormalizedCacheObject> | null>(null)
  /* const [loading, setLoading] = useState(true) */

  useEffect(() => {
    getApolloClient().then((client) => {
      setClient(client)
      /* setLoading(false) */
    })
  }, [])

  /* return <AppComponent client={client} loading={loading} /> */
  return <AppComponent client={client} />
}
