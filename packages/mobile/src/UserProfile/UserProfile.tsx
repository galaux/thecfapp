import { IUser } from '../types'
import { Text } from '@chakra-ui/react'

interface Props {
  user: IUser
}

export const UserProfile: React.FC<Props> = (props) => {
  const { user } = props

  return (
    <Text>
      ID: {user.id}
      <br />
      Name: {user.name}
    </Text>
  )
}
