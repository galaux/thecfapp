import * as React from 'react'
import * as Icon from 'react-feather'
import { Avatar, VStack, Box, Flex, Spinner, Heading } from '@chakra-ui/react'
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
import { useGetUserLazyQuery } from './user.generated'
import { loggedUserId } from '../../graphql/state/app'
import { fetchPolicy } from '../../fetch-policy'
import { COPYRIGHT_STR } from '../../utils'

const bgImg = '/assets/meghan-holmes-buWcS7G1_28-unsplash.jpg'

export const Header: React.FC = ({ children }) => {
  const userId = loggedUserId()

  const { isOpen, onOpen, onClose } = useDisclosure()

  /* FIXME Shouldn't we have this side effect out of this component?
     But this component has no page. Or should we perform the side effect
     in Layout/index.tsx? */
  const [getUser, { loading, error, data }] = useGetUserLazyQuery({
    fetchPolicy: fetchPolicy('header-user'),
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
            <DrawerContent background='_thunder' color='white'>
              <DrawerHeader>
                <VStack>
                  <Avatar name={data.user.name} />
                  <Heading textTransform='uppercase'>{data.user.name}</Heading>
                </VStack>
              </DrawerHeader>
              <DrawerBody ml='7%'>
                <Link to='/'>
                  <Flex>
                    <Icon.Home color='gray' size='1.1rem' />{' '}
                    <Text ml='7%'>Dashboard</Text>
                  </Flex>
                </Link>

                <Link to='/user-communities'>
                  <Flex>
                    <Icon.Box color='gray' size='1.1rem' />
                    <Text ml='7%'>My communities</Text>
                  </Flex>
                </Link>

                <Link to='/wod-list'>
                  <Flex>
                    <Icon.Activity color='gray' size='1.1rem' />
                    <Text ml='7%'>My WoDs</Text>
                  </Flex>
                </Link>

                <Link to='/find-community'>
                  <Flex>
                    <Icon.Search color='gray' size='1.1rem' />
                    <Text ml='7%'>Find a community</Text>
                  </Flex>
                </Link>

                <Flex>
                  <Icon.Bell color='gray' size='1.1rem' />
                  <Text ml='7%'>Notifications</Text>
                </Flex>

                <Link to='/user-profile'>
                  <Flex>
                    <Icon.User color='gray' size='1.1rem' />
                    <Text ml='7%'>Profile</Text>
                  </Flex>
                </Link>

                <Flex>
                  <Icon.HelpCircle color='gray' size='1.1rem' />
                  <Text ml='7%'>Help</Text>
                </Flex>
              </DrawerBody>

              <Flex>
                <Icon.Shield color='gray' size='1.1rem' />
                <Text ml='7%'>About us</Text>
              </Flex>
              <DrawerFooter borderTopWidth='1px' borderColor='_gray' mx='10%'>
                <Text gridArea='Footer' fontSize='xs' color='_gray'>
                  {COPYRIGHT_STR}
                </Text>
              </DrawerFooter>
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
