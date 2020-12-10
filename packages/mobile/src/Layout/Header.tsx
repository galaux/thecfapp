import * as React from 'react'
import * as Icon from 'react-feather'
import { Box, Flex, Spinner, Heading } from '@chakra-ui/react'
import { Link } from '@reach/router'
import {
  Drawer,
  DrawerBody,
  DrawerFooter,
  DrawerHeader,
  DrawerOverlay,
  DrawerContent,
  useDisclosure,
  Text,
} from '@chakra-ui/react'
import { UserContext } from '../App'
import { useGetUserLazyQuery } from './user.generated'

const bgImg = '/assets/meghan-holmes-buWcS7G1_28-unsplash.jpg'

export const Header: React.FC = ({ children }) => {
  const userId = React.useContext(UserContext)

  const { isOpen, onOpen, onClose } = useDisclosure()

  /* FIXME Shouldn't we have this side effect out of this component?
     But this component has no page. Or should we perform the side effect
     in Layout/index.tsx? */
  const [getUser, { loading, error, data }] = useGetUserLazyQuery({
    fetchPolicy: 'cache-and-network',
    variables: { userId },
  })

  /* FIXME keep in state what page the user is currently on so that if clicking
     on the menu item for the page the user already is at, we just close the menu */

  return (
    <>
      {loading && <Spinner />}
      {error && <p>{error}</p> /* TODO */}
      {data && (
        <Drawer placement='left' onClose={onClose} isOpen={isOpen}>
          <DrawerOverlay>
            <DrawerContent background='_shark' color='white'>
              <DrawerHeader>{data.user.name}</DrawerHeader>
              <DrawerBody>
                <Text>
                  <Icon.Home />
                  Dashboard
                </Text>
                <Icon.Box />
                <Text>My communities</Text>
                <Link to='/wod-list'>
                  <Icon.Activity />
                  My WoDs
                </Link>
                <Icon.Search />
                <Text>Find a community</Text>
                <Icon.Bell />
                <Text>Notifications</Text>
                <Link to='/user-profile'>
                  <Icon.User />
                  Profile
                </Link>
                <Icon.HelpCircle />
                <Text>Help</Text>
              </DrawerBody>
              <Icon.Shield />
              <Text>About Us</Text>
              <DrawerFooter></DrawerFooter>
            </DrawerContent>
          </DrawerOverlay>
        </Drawer>
      )}

      <Box
        pos='relative'
        bgColor='_thunder'
        borderBottomLeftRadius={12}
        borderBottomRightRadius={12}
        overflow='hidden'
        p={6}
        _after={{
          content: '""',
          pos: 'absolute',
          top: 0,
          right: 0,
          bottom: 0,
          left: 0,
          background: `url(${bgImg})`,
          bgPos: 'center',
          bgSize: 'cover',
          opacity: 0.3,
          zIndex: 5,
        }}
      >
        <Flex
          gridArea='Header'
          justifyContent='space-between'
          pos='relative'
          zIndex={10}
          mb={6}
        >
          <Icon.Menu
            onClick={() => {
              getUser()
              onOpen()
            }}
          />

          <Link to='/'>
            <Heading as='h1' fontSize='xl'>
              THECFAPP
            </Heading>
          </Link>

          <Link to='/user-profile'>
            <Icon.User />
          </Link>
        </Flex>

        {children}
      </Box>
    </>
  )
}
