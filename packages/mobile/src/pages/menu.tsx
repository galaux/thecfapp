import { Menu } from '../components/Menu'
import { fetchPolicy } from '../fetch-policy'
import { loggedUserId } from '../graphql/state/app'
import { useMenuUserLazyQuery } from '../user-menu.generated'
import { UseDisclosureReturn } from '@chakra-ui/react'
import { useDisclosure } from '@chakra-ui/react'
import React, { createContext } from 'react'

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

export const MenuPage: React.FC = (props) => {
  const { children } = props
  const userId = loggedUserId()

  const [
    getMenuUser,
    { loading: loadingMenuUser, error: errorMenuUser, data: dataMenuUser },
  ] = useMenuUserLazyQuery({
    fetchPolicy: fetchPolicy('header-user'),
    variables: { userId },
  })

  /* FIXME keep in state what page the user is currently on so that if clicking
     on the menu item for the page the user already is at, we just close the menu */
  const menuDisclosure = useDisclosure({
    onOpen: getMenuUser,
  })

  return (
    <>
      <Menu
        loading={loadingMenuUser}
        error={errorMenuUser}
        user={dataMenuUser?.user}
        menuDisclosure={menuDisclosure}
      />
      <MenuContext.Provider value={menuDisclosure}>
        {children}
      </MenuContext.Provider>
    </>
  )
}
