import { UserProfileUserFragment as User } from './user.generated'
import { Text } from '@chakra-ui/react'

interface Props {
  user: User
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
