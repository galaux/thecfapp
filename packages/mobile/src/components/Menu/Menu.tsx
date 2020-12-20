import React, { useEffect } from 'react'
import * as Icon from 'react-feather'
import { Avatar, VStack, Flex, Spinner, Heading } from '@chakra-ui/react'
import { Link } from '@reach/router'
import {
  Drawer,
  DrawerBody,
  DrawerFooter,
  DrawerHeader,
  DrawerOverlay,
  DrawerContent,
  Text,
} from '@chakra-ui/react'
import { COPYRIGHT_STR } from '../../utils'
import { loggedUserId } from '../../graphql/state/app'
import { useMenuUserLazyQuery } from '../../pages/user/menu/user-menu.generated'
import { fetchPolicy } from '../../fetch-policy'

interface Props {
  onClose: () => void
  isOpen: boolean
}

export const Menu: React.FC<Props> = (props) => {
  const { onClose, isOpen } = props

  const userId = loggedUserId()

  // FIXME Move this side effect out of this component
  const [getMenuUser, { loading, error, data }] = useMenuUserLazyQuery({
    fetchPolicy: fetchPolicy('header-user'),
    variables: { userId },
  })

  let isMounted = true
  useEffect(() => {
    if (isMounted) {
      getMenuUser()
    }
    return () => {
      // eslint-disable-next-line react-hooks/exhaustive-deps
      isMounted = false
    }
  }, [])

  const user = data?.user

  return (
    <Drawer placement='left' onClose={onClose} isOpen={isOpen}>
      <DrawerOverlay>
        <DrawerContent background='_thunder' color='white'>
          <DrawerHeader>
            {loading && <Spinner />}
            {error && <p>{error}</p>}
            {user && (
              <VStack>
                <Avatar name={user.name} />
                <Heading textTransform='uppercase'>{user.name}</Heading>
              </VStack>
            )}
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
  )
}
