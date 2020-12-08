import React from 'react'
import { ChakraProvider, Spinner, extendTheme } from '@chakra-ui/react'
/* import logo from './logo.svg'; */
import './App.css'
/* import { ColorModeSwitcher } from "./ColorModeSwitcher" */
import AddScorePage from './Score'
import WodListPage from './Wod'
import DashboardPage from './Dashboard'
import UserProfilePage from './UserProfile'
import { ApolloProvider } from '@apollo/client'
import { ApolloClient, NormalizedCacheObject } from '@apollo/client'
import { Router } from '@reach/router'
import { userId } from './apollo-client'

const colors = {
  _gray: '#808080',
  _shark: '#1D1D22',
  _thunder: '#2F2A2F',
}

const customTheme = extendTheme({ colors })

type Props = {
  client: ApolloClient<NormalizedCacheObject> | null
}

const App: React.FC<Props> = ({ client }) => {
  if (!client) {
    return <Spinner />
  }

  return (
    <ApolloProvider client={client}>
      <ChakraProvider theme={customTheme}>
        <Router>
          <AddScorePage path='/add-score/:wodId' />
          <DashboardPage path='/' userId={userId} />
          <UserProfilePage path='user-profile' userId={userId} />
          <WodListPage path='wod-list' userId={userId} />
        </Router>
      </ChakraProvider>
    </ApolloProvider>
  )
}

export default App
