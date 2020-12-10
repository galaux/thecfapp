import React from 'react'
import { ChakraProvider, Spinner, extendTheme } from '@chakra-ui/react'
import './App.css'
import NewScorePage from './NewScore'
import UserWodsPage from './UserWods'
import DashboardPage from './Dashboard'
import UserProfilePage from './UserProfile'
import { ApolloProvider } from '@apollo/client'
import { ApolloClient, NormalizedCacheObject } from '@apollo/client'
import { Router } from '@reach/router'

// Local
export const API_HOST = 'http://localhost:4000/graphql'
const userId = '499487df-3e84-4fa8-ad60-c9444dcdf7f5'

// Prod
// export const API_HOST = 'https://thecfapp.herokuapp.com/graphql'
// const userId = "366a8963-6f81-4cfb-9a0c-c2c2629bd556"

export const UserContext = React.createContext<string>(userId)

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
        <UserContext.Provider value={userId}>
          <Router>
            <NewScorePage path='/add-score/:wodId' />
            <DashboardPage path='/' userId={userId} />
            <UserProfilePage path='/user-profile' userId={userId} />
            <UserWodsPage path='/wod-list' userId={userId} />
          </Router>
        </UserContext.Provider>
      </ChakraProvider>
    </ApolloProvider>
  )
}

export default App
