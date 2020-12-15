import {
  AddUserToCommunityInput,
  RemoveUserFromCommunityInput,
} from '../../../generated/graphql'
import { Context } from '../../../types'
import { UserCommunity } from '../../../domain/user-community'

async function addUserToCommunity(
  { conn }: Context,
  input: AddUserToCommunityInput,
): Promise<void> {
  conn.getRepository(UserCommunity).save(
    new UserCommunity({
      communityId: input.communityId,
      userId: input.userId,
    }),
  )
}

async function removeUserFromCommunity(
  { conn }: Context,
  input: RemoveUserFromCommunityInput,
): Promise<void> {
  conn.getRepository(UserCommunity).delete({
    communityId: input.communityId,
    userId: input.userId,
  })
}

export { addUserToCommunity, removeUserFromCommunity }
