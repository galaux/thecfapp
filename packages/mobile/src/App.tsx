import getApolloClient from './graphql/apollo-client'
import { __loggedUserId } from './graphql/state/app'
import { CommunityDetailPage } from './pages/community/detail'
import { FindCommunityPage } from './pages/community/find'
import { NewCommunityPage } from './pages/community/new'
import { UserCommunitiesPage } from './pages/community/users'
import { DashboardPage } from './pages/dashboard'
import LoginPage from './pages/login'
import { MenuPage } from './pages/menu'
import RegisterPage from './pages/register'
import { NewScorePage } from './pages/score/new'
import { ScoreboardPage } from './pages/score/scoreboard'
import { UserProfilePage } from './pages/user/profile'
import { NewWodPage } from './pages/wod/new'
import { UserWodsPage } from './pages/wod/users'
import FontStyles from './styles/fonts/fontStyles'
import GlobalStyle from './styles/theme'
import { extUserToInternalId } from './utils'
import {
  ApolloClient,
  ApolloProvider,
  NormalizedCacheObject,
} from '@apollo/client'
import { Auth0Context } from '@auth0/auth0-react'
import { ChakraProvider, extendTheme } from '@chakra-ui/react'
import { Spinner } from '@chakra-ui/react'
import { Router } from '@reach/router'
import React, { useContext, useEffect, useState } from 'react'
import 'react-dates/initialize'
import 'react-dates/lib/css/_datepicker.css'

const colors = {
  _gray: '#808080',
  _shark: '#1D1D22',
  _thunder: '#2F2A2F',
}

const customTheme = extendTheme({ colors })

const App: React.FC = () => {
  const {
    getIdTokenClaims,
    isLoading,
    isAuthenticated,
    error,
    user,
    loginWithRedirect,
  } = useContext(Auth0Context)
  const [gqlClient, setGqlClient] =
    useState<ApolloClient<NormalizedCacheObject> | undefined>(undefined)

  useEffect(() => {
    const logUser = async () => {
      if (!isAuthenticated && !isLoading) {
        await loginWithRedirect()
      }
      if (isAuthenticated && !gqlClient) {
        const theClient = await getApolloClient(getIdTokenClaims)
        setGqlClient(theClient)
      }
    }
    logUser()
  })

  if (isLoading || !gqlClient) {
    return <Spinner />
  }

  if (error) {
    return <p>{error}</p>
  }

  if (!user?.sub) {
    return <p>Error: bad external user</p>
  }

  // FIXME Get rid of the last piece of code to use `__loggedUserId`
  __loggedUserId(extUserToInternalId(user))

  return (
    <ApolloProvider client={gqlClient}>
      <FontStyles />
      <GlobalStyle />
      <ChakraProvider theme={customTheme}>
        <MenuPage>
          <Router>
            <CommunityDetailPage path='/community/:communityId' />
            <NewWodPage path='/new-wod' />
            <DashboardPage path='/' />
            <FindCommunityPage path='/find-community' />
            <NewCommunityPage path='/new-community' />
            <NewScorePage path='/new-score' />
            <ScoreboardPage path='/scoreboard/:wodId' />
            <UserCommunitiesPage path='/user-communities' />
            <UserProfilePage path='/user-profile' />
            <UserWodsPage path='/wod-list' />
            <LoginPage path='/login' />
            <RegisterPage path='/register' />
          </Router>
        </MenuPage>
      </ChakraProvider>
    </ApolloProvider>
  )
}

export default App
