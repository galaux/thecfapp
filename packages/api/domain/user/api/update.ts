import { Context } from '../../../types'
import { UserCommunity } from '../../../domain/user-community'

async function addUserToCommunity(
  { conn }: Context,
  userId: string,
  communityId: string,
): Promise<void> {
  conn.getRepository(UserCommunity).save(
    new UserCommunity({
      communityId: communityId,
      userId: userId,
    }),
  )
}

async function removeUserFromCommunity(
  { conn }: Context,
  userId: string,
  communityId: string,
): Promise<void> {
  conn.getRepository(UserCommunity).delete({
    communityId: communityId,
    userId: userId,
  })
}

export { addUserToCommunity, removeUserFromCommunity }
