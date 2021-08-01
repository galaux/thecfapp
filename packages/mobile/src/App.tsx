import { noUserLoggedIn } from './graphql/state/app'
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
import { ApolloClient, NormalizedCacheObject } from '@apollo/client'
import { ApolloProvider } from '@apollo/client'
import { ChakraProvider, extendTheme } from '@chakra-ui/react'
import { Router } from '@reach/router'
import React from 'react'
import 'react-dates/initialize'
import 'react-dates/lib/css/_datepicker.css'

/* A user with this ID is seeded in the DB at start-up, don't change or sync
   sync with @thecfapp/api/db/db-seeder value and re-seed the DB */
/* const defaultUserId = 'df56c5a2-3f0c-459a-b158-d1a999714247' */

const colors = {
  _gray: '#808080',
  _shark: '#1D1D22',
  _thunder: '#2F2A2F',
}

const customTheme = extendTheme({ colors })

type Props = {
  client: ApolloClient<NormalizedCacheObject>
}

const App: React.FC<Props> = (props) => {
  const { client } = props

  /* loggedUserId(defaultUserId) */

  if (noUserLoggedIn()) {
    return <LoginPage />
  }

  return (
    <ApolloProvider client={client}>
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
