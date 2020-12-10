import { AUserFragment } from './user.generated'
import { Text } from '@chakra-ui/react'

interface Props {
  user: AUserFragment
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
