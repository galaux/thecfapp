import React from 'react'
import { ChakraProvider, Spinner, extendTheme } from '@chakra-ui/react'
import './App.css'
import NewScorePage from './NewScore'
import { UserWodsPage } from './pages/wod/users'
import { DashboardPage } from './pages/dashboard'
import UserProfilePage from './UserProfile'
import { ApolloProvider } from '@apollo/client'
import { ApolloClient, NormalizedCacheObject } from '@apollo/client'
import { Router } from '@reach/router'
import { loggedUserId } from './graphql/state/app'
import { FindCommunityPage } from './pages/community/find'
import { NewCommunityPage } from './pages/community/new'
import { UserCommunitiesPage } from './pages/community/users'
import { CommunityDetailPage } from './pages/community/detail'

/* A user with this ID is seeded in the DB at start-up, don't change or sync
   sync with @thecfapp/api/db/db-seeder value and re-seed the DB */
const userId = 'df56c5a2-3f0c-459a-b158-d1a999714247'

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

  // FIXME temporarily hard-coding the logged user ID
  loggedUserId(userId)

  return (
    <ApolloProvider client={client}>
      <ChakraProvider theme={customTheme}>
        <Router>
          <CommunityDetailPage path='/community/:communityId' />
          <DashboardPage path='/' />
          <FindCommunityPage path='/find-community' />
          <NewCommunityPage path='/new-community' />
          <NewScorePage path='/add-score/:wodId' />
          <UserCommunitiesPage path='/user-communities' />
          <UserProfilePage path='/user-profile' />
          <UserWodsPage path='/wod-list' />
        </Router>
      </ChakraProvider>
    </ApolloProvider>
  )
}

export default App
