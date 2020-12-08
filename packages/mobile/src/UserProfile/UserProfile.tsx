import { GetUserUserFragment } from '../types'
import { Text } from '@chakra-ui/react'

interface Props {
  user: GetUserUserFragment
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
