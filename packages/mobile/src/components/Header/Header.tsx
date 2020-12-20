import * as React from 'react'
import * as Icon from 'react-feather'
import { Box, Flex, Heading, useDisclosure } from '@chakra-ui/react'
import { Link } from '@reach/router'
import { Menu } from '../Menu'

const bgImg = '/assets/meghan-holmes-buWcS7G1_28-unsplash.jpg'

export const Header: React.FC = ({ children }) => {
  /* FIXME keep in state what page the user is currently on so that if clicking
     on the menu item for the page the user already is at, we just close the menu */

  const { isOpen, onOpen, onClose } = useDisclosure()

  return (
    <>
      <Menu onClose={onClose} isOpen={isOpen} />

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
          <Icon.Menu onClick={onOpen} />

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
