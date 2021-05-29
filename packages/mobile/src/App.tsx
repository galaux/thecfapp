import { Menu } from './components/Menu'
import { fetchPolicy } from './fetch-policy'
import { loggedUserId } from './graphql/state/app'
import { CommunityDetailPage } from './pages/community/detail'
import { FindCommunityPage } from './pages/community/find'
import { NewCommunityPage } from './pages/community/new'
import { UserCommunitiesPage } from './pages/community/users'
import { DashboardPage } from './pages/dashboard'
import LoginPage from './pages/login'
import RegisterPage from './pages/register'
import { NewScorePage } from './pages/score/new'
import { ScoreboardPage } from './pages/score/scoreboard'
import { UserProfilePage } from './pages/user/profile'
import { NewWodPage } from './pages/wod/new'
import { UserWodsPage } from './pages/wod/users'
import FontStyles from './styles/fonts/fontStyles'
import GlobalStyle from './styles/theme'
import { useMenuUserLazyQuery } from './user-menu.generated'
import { ApolloProvider } from '@apollo/client'
import { ApolloClient, NormalizedCacheObject } from '@apollo/client'
import {
  ChakraProvider,
  extendTheme,
  useDisclosure,
  UseDisclosureReturn,
} from '@chakra-ui/react'
import { Router } from '@reach/router'
import React, { createContext } from 'react'
import 'react-dates/initialize'
import 'react-dates/lib/css/_datepicker.css'

/* A user with this ID is seeded in the DB at start-up, don't change or sync
   sync with @thecfapp/api/db/db-seeder value and re-seed the DB */
const userId = 'df56c5a2-3f0c-459a-b158-d1a999714247'

const colors = {
  _gray: '#808080',
  _shark: '#1D1D22',
  _thunder: '#2F2A2F',
}

const customTheme = extendTheme({ colors })

const defaultUseDisclosureReturn = {
  isOpen: false,
  onOpen: () => null,
  onClose: () => null,
  onToggle: () => null,
  isControlled: false,
  getButtonProps: (_: any) => null,
  getDisclosureProps: (_: any) => null,
}

export const MenuContext = createContext<UseDisclosureReturn>(
  defaultUseDisclosureReturn,
)

type Props = {
  client: ApolloClient<NormalizedCacheObject>
}

const App: React.FC<Props> = (props) => {
  // FIXME temporarily hard-coding the logged user ID
  loggedUserId(userId)

  const { client } = props

  const [
    getMenuUser,
    { loading: loadingMenuUser, error: errorMenuUser, data: dataMenuUser },
  ] = useMenuUserLazyQuery({
    client,
    fetchPolicy: fetchPolicy('header-user'),
    variables: { userId },
  })

  /* FIXME keep in state what page the user is currently on so that if clicking
     on the menu item for the page the user already is at, we just close the menu */
  const menuDisclosure = useDisclosure({
    onOpen: getMenuUser,
  })

  return (
    <ApolloProvider client={client}>
      <FontStyles />
      <GlobalStyle />
      <ChakraProvider theme={customTheme}>
        <>
          <Menu
            loading={loadingMenuUser}
            error={errorMenuUser}
            user={dataMenuUser?.user}
            menuDisclosure={menuDisclosure}
          />
          <MenuContext.Provider value={menuDisclosure}>
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
          </MenuContext.Provider>
        </>
      </ChakraProvider>
    </ApolloProvider>
  )
}

export default App
