import React from 'react'
import * as Icon from 'react-feather'
import {
  Avatar,
  VStack,
  Flex,
  Heading,
  UseDisclosureReturn,
} from '@chakra-ui/react'
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
import { MenuUserFragment as User } from './menu.generated'
import { ApolloError } from '@apollo/client'
import { LoadingOr } from '../LoadingOr'
import { ErrorOr } from '../ErrorOr'

interface Props {
  menuDisclosure: UseDisclosureReturn
  loading: boolean
  error: ApolloError | undefined
  user: User | undefined
}

export const Menu: React.FC<Props> = (props) => {
  const { loading, error, user, menuDisclosure } = props
  const { onClose: closeMenu, isOpen } = menuDisclosure

  return (
    <Drawer placement='left' onClose={closeMenu} isOpen={isOpen}>
      <DrawerOverlay>
        <DrawerContent background='_thunder' color='white'>
          <DrawerHeader>
            <LoadingOr loading={loading}>
              <ErrorOr error={error}>
                {user && (
                  <VStack>
                    <Avatar name={user.name} />
                    <Heading textTransform='uppercase'>{user.name}</Heading>
                  </VStack>
                )}
              </ErrorOr>
            </LoadingOr>
          </DrawerHeader>
          <DrawerBody ml='7%'>
            <Link onClick={closeMenu} to='/'>
              <Flex>
                <Icon.Home color='gray' size='1.1rem' />{' '}
                <Text ml='7%'>Dashboard</Text>
              </Flex>
            </Link>

            <Link onClick={closeMenu} to='/user-communities'>
              <Flex>
                <Icon.Box color='gray' size='1.1rem' />
                <Text ml='7%'>My communities</Text>
              </Flex>
            </Link>

            <Link onClick={closeMenu} to='/wod-list'>
              <Flex>
                <Icon.Activity color='gray' size='1.1rem' />
                <Text ml='7%'>My WoDs</Text>
              </Flex>
            </Link>

            <Link onClick={closeMenu} to='/find-community'>
              <Flex>
                <Icon.Search color='gray' size='1.1rem' />
                <Text ml='7%'>Find a community</Text>
              </Flex>
            </Link>

            <Flex>
              <Icon.Bell color='gray' size='1.1rem' />
              <Text ml='7%'>Notifications</Text>
            </Flex>

            <Link onClick={closeMenu} to='/user-profile'>
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
